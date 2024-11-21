<template>
    <div class="main-layout">
        <div class="sidebar">
            <Sidebar />
        </div>
        <div class="content">
            <RouterView />
        </div>
    </div>
</template>

<script setup>
import Sidebar from '@/components/common/Sidebar.vue'
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
    display: block; /* 요소를 보이게 설정 */
    visibility: visible;
    opacity: 1;
    font-weight: small;
    font-style: small;
}

.content {
    flex-grow: 1;
    padding: 20px;
    overflow-y: auto;
    background-color: #ffffff;
}
</style>
