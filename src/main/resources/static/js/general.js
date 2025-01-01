
const dateFormatFnc = (date1) => {
    let date = new Date(date1);
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}   ${date.getHours()}:${date.getMinutes()}`;
}

const  controlAccessToken =() => {
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
}

const logout =() => {
    // Token'ları temizle
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');

    // Kullanıcıya mesaj göster
    alert("Çıkış başarılı!");

    // Sayfayı login.html'e yönlendir
    window.location.href = '/';
}