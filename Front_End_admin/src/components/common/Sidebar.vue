<template>
    <div>
        <ul class="navbar-nav flex-column" id="navbar-sidebar">
            <h5>Pages</h5>
            <br />
            <li class="nav-item">
                <RouterLink
                    class="nav-link"
                    :class="{ active: activeMenu === 'AdminMain' }"
                    :to="{ name: 'AdminMain' }"
                    @click="setActive('AdminMain')"
                >
                    Users
                </RouterLink>
            </li>

            <li class="nav-item">
                <a
                    class="nav-link"
                    :class="{ active: activeMenu === 'Notice' }"
                    :to="{ name: 'BoardList' }"
                    @click="toggleDropdown('Notice', 'collapsepage')"
                >
                    Notice
                </a>
                <ul
                    class="nav collapse"
                    id="collapsepage"
                    :class="{ show: activeMenu === 'Notice' }"
                >
                <li class="nav-item">
                    <RouterLink class="nav-link small-menu" :to="{ name: 'NoticeList' }">
                        Notice List
                    </RouterLink>
                </li>
                <li class="nav-item">
                    <RouterLink class="nav-link small-menu" :to="{ name: 'NoticeForm' }">
                        Notice Regist
                    </RouterLink>
                </li>

                </ul>
            </li>

            <li class="nav-item">
                <a
                    class="nav-link"
                    :class="{ active: activeMenu === 'Insurance' }"
                    role="button"
                    @click="toggleDropdown('Insurance', 'collapseinstructors')"
                >
                    Insurance
                </a>
                <ul
                    class="nav collapse"
                    id="collapseinstructors"
                    :class="{ show: activeMenu === 'Insurance' }"
                >
                    <li class="nav-item">
                        <RouterLink class="nav-link small-menu" :to="{ name: 'InsuranceList' }">
                            Insurance List
                        </RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link small-menu" :to="{ name: 'InsuranceForm' }">
                            Insurance Form
                        </RouterLink>
                    </li>
                </ul>
            </li>
        </ul>
        <!-- 이동된 종료 버튼 -->
        <div class="logout-footer">
            <a
                class="h5 mb-0 text-body logout-btn"
                @click="store.adminLogout" 
                data-bs-toggle="tooltip"
                data-bs-placement="top"
                title="Sign out"
                role="button"
            >
                <i class="bi bi-power"></i>
            </a>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useAdminStore } from '@/store/useAdminStore.js'; 

const activeMenu = ref('AdminMain');
const store = useAdminStore(); 

const setActive = (menu) => {
    activeMenu.value = menu;
};

const toggleDropdown = (menu, collapseId) => {
    if (activeMenu.value === menu) {
        activeMenu.value = null;
    } else {
        activeMenu.value = menu;
    }
};
</script> 

<style scoped>
@font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
}

body {
    font-family: 'GmarketSansMedium';
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
}

.main-layout {
    display: flex;
    height: 100vh;
    width: 100vw;
}

.sidebar {
    width: 250px;
    height: 100vh;
    background-color: #f8f9fa;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
    overflow-y: auto;
    padding: 20px;
    color: #000;
    display: flex;
    flex-direction: column;
    position: relative; /* 추가: 자식 요소의 위치 기준 */
}

.navbar-nav {
    margin-top: 50px;
    padding: 0;
}

.nav-item {
    margin-bottom: 10px;
    padding-left: 20px;
    list-style: none;
    position: relative;
    width: 100%;
}

.nav.collapse {
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s ease;
}

.nav.collapse.show {
    max-height: 500px;
}

.nav-item > .nav-link {
    font-size: 16px;
    font-weight: bold;
    color: #000;
}

.nav.collapse .nav-item > .small-menu {
    font-size: 12px;
    color: #555;
}

.nav-link {
    text-decoration: none;
    padding: 10px;
    display: block;
    border-radius: 5px;
}

.nav-link.active {
    color: #043873 !important;
}

.nav-link:hover {
    background-color: #f0f0f0;
    color: #007bff;
}


.logout-footer {
    position: absolute; 
    bottom: 20px; 
    left: 20px; 
    text-align: right; 
    width: fit-content; 
}

.logout-btn {
    color: #000;
    font-size: 18px;
    text-decoration: none;
    display: inline-block;
    border-radius: 5px;
    padding: 10px;
    transition: background-color 0.3s, color 0.3s;
}

.logout-btn:hover {
    background-color: #a3232373;
}
</style>
