<template>
    <div class="main-layout">
        <!-- Header -->
        <Header />

        <div class="body-layout">
            <!-- Sidebar -->
            <div class="sidebar">
                <Sidebar />
            </div>

            <!-- Main Content -->
            <div class="content">
                <RouterView />
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from '@/components/common/Sidebar.vue';
import Header from '@/components/common/HeaderBar.vue';
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
    flex-direction: column; /* 상단 헤더와 콘텐츠를 수직으로 배치 */
    height: 100vh;
    width: 100vw;
}

.body-layout {
    display: flex;
    flex: 1;
    height: calc(100vh - 60px); /* 헤더 높이를 제외한 나머지 */
    overflow: hidden;
}

.sidebar {
    width: 250px;
    height: 100%;
    background-color: #f8f9fa;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
    overflow-y: auto;
    padding: 20px;
}

.content {
    flex-grow: 1;
    padding: 20px;
    overflow-y: auto;
    background-color: #ffffff;
}
</style>
