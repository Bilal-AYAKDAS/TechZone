$(document).ready(function () {

    var accessToken = localStorage.getItem('accessToken');


    if (accessToken) {
        // Kullanıcı giriş yapmışsa
        $('#navbar-login').html('<a href="../customer/profile" class="nav-link">My Profile</a>');
        $('#logout-btn').show(); // Çıkış butonunu göster
    } else {
        // Kullanıcı giriş yapmamışsa
        $('#navbar-login').html('<a href="../public/login" class="nav-link">Login</a>');
        $('#logout-btn').hide(); // Çıkış butonunu gizle
    }

    // Favori ve Sepet ikonlarına tıklama olayını kontrol et
    $('.fa-heart').on('click', function (e) {
        if (!localStorage.getItem('accessToken')) {
            e.preventDefault(); // Sayfanın yönlendirilmesini durdur
            alert('Please login first!');
        }
    });
    $('.fa-cart-shopping').on('click', function (e) {
        if (!localStorage.getItem('accessToken')) {
            e.preventDefault(); // Sayfanın yönlendirilmesini durdur
            alert('Please login first!');
        }
    });





    let selectedCategoryIds = []; // Seçili kategori ID'leri
    let selectedBrandIds = []; // Seçili marka ID'leri

    // Kategorileri yükle
    $.ajax({
        url: 'http://localhost:8080/api/categories/getall',
        type: 'GET',
        success: function (categories) {
            const categoryRow = $('#categoryRow');
            categories.forEach(category => {
                const categoryCard = $(`
            <div class="category-card">
              <input type="checkbox" class="category-checkbox" data-category-id="${category.id}"> ${category.name}
            </div>
          `);
                categoryCard.find('input').on('change', function () {
                    const categoryId = $(this).data('category-id');
                    if (this.checked) {
                        selectedCategoryIds.push(categoryId);
                    } else {
                        selectedCategoryIds = selectedCategoryIds.filter(id => id !== categoryId);
                    }
                    loadProducts(); // Ürünleri yükle
                });
                categoryRow.append(categoryCard);
            });
        },
        error: function (error) {
            console.error('Error loading categories.', error);
        }
    });

    // Markaları yükle
    $.ajax({
        url: 'http://localhost:8080/api/brands/getall',
        type: 'GET',
        success: function (brands) {
            const brandList = $('#brandList');
            brands.forEach(brand => {
                const brandItem = $(`
            <div class="brand-item">
              <input type="checkbox" class="brand-checkbox" data-brand-id="${brand.id}"> ${brand.name}
            </div>
          `);
                brandItem.find('input').on('change', function () {
                    const brandId = $(this).data('brand-id');
                    if (this.checked) {
                        selectedBrandIds.push(brandId);
                    } else {
                        selectedBrandIds = selectedBrandIds.filter(id => id !== brandId);
                    }
                    loadProducts(); // Ürünleri yükle
                });
                brandList.append(brandItem);
            });

            $('#brands').show(); // Markalar bölümünü göster
        },
        error: function (error) {
            console.error('Error loading brands.', error);
        }
    });

    // Ürünleri yüklemek için kullanılan fonksiyon
    function loadProducts() {
        const productGrid = $('#productGrid');



        // Eski ürünleri temizlememek için, önce ekranda var olanları saklıyoruz.
        // const oldProducts = productTableBody.children();
        productGrid.empty(); // Önce tablonun içeriğini boşaltıyoruz

        let apiUrls = [];

        if (selectedCategoryIds.length > 0 && selectedBrandIds.length > 0) {
            // Hem kategori hem marka seçildiyse, her kategori ve marka kombinasyonunu ekle
            selectedCategoryIds.forEach((categoryId) => {
                selectedBrandIds.forEach((brandId) => {
                    apiUrls.push(`http://localhost:8080/api/products/getByCategoryIdAndBrandId/${categoryId}/${brandId}`);
                });
            });
        } else if (selectedCategoryIds.length > 0) {
            // Sadece kategori seçildiyse, her kategori için URL oluştur
            selectedCategoryIds.forEach((categoryId) => {
                apiUrls.push(`http://localhost:8080/api/products/getByCategoryId/${categoryId}`);
            });
        } else if (selectedBrandIds.length > 0) {
            // Sadece marka seçildiyse, her marka için URL oluştur
            selectedBrandIds.forEach((brandId) => {
                apiUrls.push(`http://localhost:8080/api/products/getByBrandId/${brandId}`);
            });
        } else {
            // Hiçbir seçim yapılmadıysa tüm ürünleri getir
            apiUrls.push('http://localhost:8080/api/products/getall');
        }

        // API çağrılarını tek tek yap ve gelen veriyi ekrana ekle
        apiUrls.forEach((url) => {
            $.ajax({
                url: url,
                type: 'GET',
                success: function (products) {
                    products.forEach(product => {
                        const card = $(`
                      <div class="col-md-3">
                        <div class="card mb-4" data-product-id="${product.id}">
                          <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                          <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">Fiyat: ${product.price} TL</p>
                            <p class="card-text">${product.description || ''}</p>
                            <div class="card-buttons">
                              <button class="btn btn-primary btn-cart" style="margin-bottom: 10px;">Add to Cart</button>
                              <button class="btn btn-danger btn-favori">Add to Favorites</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    `);
                        productGrid.append(card); // Ürünü grid'e ekle
                    });
                },
                error: function (error) {
                    console.error('Error while loading products:', error);
                }
            });
        });
    }
    loadProducts();



    // "Sepete Ekle" butonuna tıklama eventi
    $(document).on('click', '.btn-cart', function () {
        const productCard = $(this).closest('.card'); // En yakın kartı bul
        const productId = productCard.data('product-id'); // Ürün ID'sini al
        const productName = productCard.find('.card-title').text(); // Ürün adını al
        const userId = localStorage.getItem('accessToken'); // Kullanıcı ID'sini Local Storage'dan al

        if (!userId) {
            alert('Please login first!');
            return;
        }

        // Gönderilecek veri
        const cartItemData = {
            productId: productId, // Ürünün ID'si
            quantity: 1 // Adet
        };

        // Backend'e POST isteği gönder
        $.ajax({
            url: 'http://localhost:8080/api/cart/addCartItem',
            type: 'POST',
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${userId}` // Token'ı Authorization başlığına ekle
            },
            data: JSON.stringify(cartItemData),
            success: function (response) {
                alert(`${productName} successfully added to your cart!`);
            },
            error: function (error) {
                console.error('Error while adding to cart.', error);
                alert('Error while adding to cart.');
            }
        });

    });




    // "Favorilere Ekle" butonuna tıklama eventi
    $(document).on('click', '.btn-favori', function () {
        const productCard = $(this).closest('.card'); // En yakın kartı bul
        const productId = productCard.data('product-id'); // Ürün ID'sini al
        const productName = productCard.find('.card-title').text(); // Ürün adını al
        const userId = localStorage.getItem('accessToken'); // Kullanıcı ID'sini Local Storage'dan al

        if (!userId) {
            alert('Please login first!');
            return;
        }

        // Mevcut favorileri kontrol etmek için GET isteği yap
        $.ajax({
            url: 'http://localhost:8080/api/favoriProducts/getall',
            type: 'GET',
            headers: {
                'Authorization': `Bearer ${userId}`
            },
            success: function (favoriteProducts) {
                const isAlreadyFavorite = favoriteProducts.some(fav => fav.product.id === productId);

                if (isAlreadyFavorite) {
                    alert('This product is already in your favorites.');
                } else {
                    // Favorilere ekleme
                    $.ajax({
                        url: 'http://localhost:8080/api/favoriProducts/add',
                        type: 'POST',
                        contentType: 'application/json',
                        headers: {
                            'Authorization': `Bearer ${userId}`
                        },
                        data: JSON.stringify({
                            productId: productId // Sadece productId gönder
                        }),
                        success: function () {
                            alert(`${productName} successfully added to your favorites!`);
                        },
                        error: function (error) {
                            console.error('Error while adding to favorites:', error);
                            alert('Error while adding to favorites.');
                        }
                    });
                }
            },
            error: function (error) {
                console.error('An error occurred while checking favorites:', error);
                alert('An error occurred while checking favorites.');
            }
        });
    });

});

function logout() {
    // Token'ları temizle
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');

    // Kullanıcıya mesaj göster
    alert("Logout successful!");

    // Sayfayı login.html'e yönlendir
    window.location.href = '/';
}




