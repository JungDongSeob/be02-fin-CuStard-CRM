<template>
  <div>
    <section v-for="(categorySlides, category) in categoriesWithSlides" :key="category">
      <div :class="'div div' + category" v-html="getCategoryName(category)" @click="goToCategory(category)"> </div>
      <Carousel :itemsToShow="5" :wrapAround="true" :transition="500">
        <Slide v-for="product in categorySlides" :key="product.idx">
          <div class="carousel__slide product">
            <div class="carousel__item-column">
              <img :src="product.productImage" alt="Slide Image" @click="goToProduct(product.idx)">
            </div>
            <div class="carousel__item-column" @click="goToProduct(product.idx)">
              <div class="carousel__item-details">
                <div class="carousel__item-title">{{ product.productName }}</div>
                <div class="carousel__item-price">{{ formatNumber(product.productPrice)}}원</div>
              </div>
            </div>
          </div>
        </Slide>
        <template #addons>
          <Navigation />
          <Pagination />
        </template>
      </Carousel>
    </section>
    <!-- 의류 버튼 -->
    <button class="category-button clothing" @click="goToCategory('1')">more +</button>
    <!-- 뷰티 버튼 -->
    <button class="category-button beauty" @click="goToCategory('2')">more +</button>
    <!-- 식품 버튼 -->
    <button class="category-button food" @click="goToCategory('3')">more +</button>
    <!-- 스포츠/레저 버튼 -->
    <button class="category-button sports" @click="goToCategory('4')">more +</button>
    <!-- 가전 버튼 -->
    <button class="category-button electronics" @click="goToCategory('5')">more +</button>
  </div>
</template>

<script>
import { ref, defineComponent } from 'vue';
import axios from 'axios';
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';

let backend = "http://192.168.0.31:80/api";
// let backend = "http://localhost:8080";


export default defineComponent({
  name: 'CategoryCarousels',

  components: {
    Carousel,
    Slide,
    Pagination,
    Navigation
  },

  setup() {
    const categoriesWithSlides = ref({});

    const fetchCategorySlides = async () => {
      try {
        const response = await axios.get(backend + "/product/list");
        const products = response.data.result;
        // 카테고리 별로 제품을 그룹화
        products.forEach(product => {
          if (!categoriesWithSlides.value[product.category]) {
            categoriesWithSlides.value[product.category] = [];
          }
          categoriesWithSlides.value[product.category].push(product);
        });
      } catch (error) {
        console.error('Error fetching categories and products:', error);
      }
    };

    const getCategoryName = (category) => {
      if (category === '1') {
        return '의류<br><span style="font-weight: normal; font-size:17px;">개성을 빛내줄 최신 패션 트렌드!</span>';
      }else if (category === '2') {
        return '뷰티<br><span style="font-weight: normal; font-size:17px;">아름다움의 진정한 의미!</span>';
      }
      else if (category === '3') {
        return '식품<br><span style="font-weight: normal; font-size:17px;">건강과 맛의 완벽한 조화!</span>';
      }
      else if (category === '4') {
        return '스포츠/레저<br><span style="font-weight: normal; font-size:17px;">활동적인 당신을 위한 최적의 선택!</span>';
      }
      else if (category === '5') {
        return '가전<br><span style="font-weight: normal; font-size:17px;">혁신적인 기술로 더욱 편리하고 스마트한 생활!</span>';
      }
      return category; // 기본적으로 카테고리 번호를 그대로 반환
    };

    fetchCategorySlides();

    return {
      categoriesWithSlides,
      getCategoryName
    };
  },

  methods: {
    async goToProduct(idx) {
      this.$router.push(`/product/${idx}`)
    },
    async goToCategory(category) {
      this.$router.push(`/category/${category}/0`)
    },
    formatNumber(value) {
      return new Intl.NumberFormat().format(value);
    }
  }
});
</script>

  <style>
  @font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 200;
    font-style: normal;
  }

html, body {
	font-family: 'Pretendard';
}

.carousel {
    margin-left: 200px !important;
    margin-right: 200px !important;
}
  .div.div1 {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-weight: bold;
  //text-shadow:
  //  2px 2px 4px rgba(0, 0, 0, 0.3);
  color:#333;
  align-content: stretch;
  font-size: 22px;
  padding: 65px;
  background-image: url("../assets/Gray\ Minimalist\ New\ Collection\ Banner.gif");
  background-position: 100% 49%;
  background-size: 1000px cover;
  background-color: #e2e2e2;
  background-repeat: no-repeat;
}
.div.div2  {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-weight: bold;
  //text-shadow:
  //  2px 2px 4px rgba(0, 0, 0, 0.3);
  color:#333;
  align-content: stretch;
  margin-top: 45px;
  font-size: 22px;
  padding: 65px;
  background-image: url("../assets/Baeuty.gif");
  background-position: 100% 50%;
  background-size: 2200px cover;
  background-color: #CFC9C4;
  background-repeat: no-repeat;
}
.div.div3  {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-weight: bold;
  //text-shadow:
  //  2px 2px 4px rgba(0, 0, 0, 0.3);
  color:#333;
  align-content: stretch;
  margin-top: 45px;
  font-size: 22px;
  padding: 65px;
  background-image: url("../assets/burger.gif");
  background-position: 130% 50%;
  background-size: 55% 150%;
  background-color: #fff;
  background-repeat: no-repeat;
}
.div.div4  {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-weight: bold;
  //text-shadow:
  //  2px 2px 4px rgba(0, 0, 0, 0.3);
  color:#333;
  align-content: stretch;
  margin-top: 45px;
  font-size: 22px;
  padding: 65px;
  background-image: url("../assets/sakai.gif");
  background-position: 50% 49%;
  background-size: 900px 185%;
  background-color: #a1a1a1;
  background-repeat: no-repeat;
}
.div.div5  {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-weight: bold;
  //text-shadow:
  //  2px 2px 4px rgba(0, 0, 0, 0.3);
  color:#333;
  align-content: stretch;
  margin-top: 45px;
  font-size: 22px;
  padding: 65px;
  background-image: url("../assets/home.gif");
  background-position: 185% 49%;
  background-size: 1000px cover;
  background-color: #f0f1f1;
  background-repeat: no-repeat;
}
  .div2{
    display: flex;
    flex-direction: column;
    justify-content: left;
  }
 .carousel__icon {
    width: var(--vc-icn-width);
    height: var(--vc-icn-width);
    fill: currentColor;
    border: 2px solid black;
    background-color: black;
    border-radius: 10px;
    color: white;
}
.carousel__prev {
  position: absolute;
  top: 205px;
  left: -14px;
}

.carousel__next {
  position: absolute;
  top: 205px;
  right: -13px;
}
  .carousel__slide {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .carousel__item-column {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    -webkit-line-clamp: 1;
  }

  .carousel__item {
    min-height: 200px;
    width: 100%;
    background-color: blue;
    color: var(--vc-clr-white);
    font-size: 20px;
    border-radius: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .carousel__slide {
    padding: 10px;
    width: 150px;
    height: 400px;
    bottom: -50px;
    display: flex;
    position: relative;
  }

  .carousel__item-details {
    text-align: center;
    margin-top: 10px;
    text-align: justify;
    /* border: 1px solid black; */
    width: 120px;
    height: 100px;
    overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-break:break-all;

  }

  .carousel__item-title {
    font-weight: bold;
  }

  .carousel__item-price {
    margin-top: 5px;
    font-size: 17px;
    font-weight: lighter;
  }

  .carousel__pagination-button{
    width: 17px;
  }
  img {
    width: 200px;
    height: 200px;
    object-fit: contain;
}
.category-button {
    position: absolute;
    top: 10px;
    right: 10px;
  border: none;
  background: rgba(209, 209, 209, 0.48);
  font-size: 15px;
  transition: all 0.25s ease 0s;
 }

  .clothing {
    position: absolute;
    top: 350px;
    right: 100px;
  }

  .beauty {
    position: absolute;
    top: 1000px;
    right: 100px;
  }

  .food {
    position: absolute;
    top: 1650px;
    right: 100px;
  }

  .sports {
    position: absolute;
    top: 2300px;
    right: 100px;
  }

  .electronics {
    position: absolute;
    top: 2950px;
    right: 100px;
  }
  .category-button:hover {
  color: #fff;
    background: #2F3438;
  }

  </style>
