<template>
  <div class="signup-content">
    <div class="all">
    <div class="signup-container">
      <div class="text-with-image-sign">
        <img src="../assets/custard-logo.png" alt="Custard 로고">
      </div>
      <br>
      <div>
        <div class="signupfont"><h3>회원가입</h3></div>
      </div>
      <br>
      <hr class="line">
      <br>

      <form id="signupForm" @submit.prevent="signup">
        <div class="insertEmail"><h5>이메일</h5></div>
        <input placeholder="이메일" type="email" id="customerEmail" v-model="customerSignup.customerEmail" maxlength="100">
        <br>
        <div class="insertpassword"><h5>비밀번호</h5></div>
        <div class="insertpassword2">영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.</div>
        <input placeholder="비밀번호" type="password" id="customerPwd" v-model="customerSignup.customerPwd" maxlength="100">
        <br>
        <div class="insertname"><h5>이름</h5></div>
        <input placeholder="이름" type="text" id="customerName" v-model="customerSignup.name" maxlength="100">
        <br>
        <div class="insertage"><h5>나이</h5></div>
        <input placeholder="나이" type="text" id="customerAge" v-model="customerSignup.age" maxlength="3">
        <br>
        <div class="insertgender"><h5>성별</h5></div>
        <select v-model="customerSignup.gender" style="width: 400px;">
          <option value="">선택 안함</option>
          <option value="female">여자</option>
          <option value="male">남자</option>
        </select>
        <br>
        <div class="insertaddress"><h5>배송지</h5></div>
        <textarea placeholder=" 배송지를 입력해주세요" id="customerAddress" v-model="customerSignup.address" style="width: 395px;"></textarea>
        <br>
        <div class="insertcheck"><h5>약관동의</h5></div>
        <div class="consent-options">
          <div class="consent-option" v-for="(value, name) in consent" :key="name">
            <label class="checkbox-label">
              <div class="checkbox-wrapper">
                <div class="check-icon"><span class="_check_12"></span></div>
                <input type="checkbox" v-model="consent[name]" class="checkbox-input">
              </div>
              <span class="consent-text">{{ getConsentLabel(name) }}</span>
            </label>
          </div>
        </div>
        <br>

        <input class="loginsubmit" type="submit" value="가입하기">
      </form>
    </div>
    </div>
  </div>

</template>

<script>
import { mapStores } from 'pinia'
import { useCustomerStore } from '@/stores/useCustomerStore';

export default {
  data() {
    return {
      customerSignup: {
        customerEmail: '',
        customerPwd: '',
        name:'',
        age:'',
        gender:'',
        address:'',

      },
      consent: {
        agreeAll: false,
        agree1: false,
        agree2: false,
        agree3: false,
        agreeMarketUsage: false,
        agreePromotion: false
      }
    };
  },
  computed: {
    ...mapStores(useCustomerStore),
    filteredConsent() {
      // "agreeAll" 항목을 제외한 consent 객체의 복사본을 반환합니다.
      const filtered = { ...this.consent };
      delete filtered.agreeAll; // "agreeAll" 항목을 제거합니다.
      return filtered;
    }
  },
  watch: {
    'consent.agreeAll'(newVal) {
      Object.keys(this.consent).forEach(key => {
        this.consent[key] = newVal;
      });
    }
  },
  methods: {
    async signup() {
      let result = await this.customerStore.signup(this.customerSignup)
      if (result === true) {
        window.location.href = "/login";
      } else {
        alert("회원 가입 실패");
        this.resetSignupForm();
      }
    },
    resetSignupForm() {
      this.customerSignup = {
        customerEmail: '',
        customerPwd: '',
        name:'',
        age:'',
        gender:'',
        address:'',
      };
      this.resetConsent();
    },
    resetConsent() {
      Object.keys(this.consent).forEach(key => {
        this.consent[key] = false;
      });
    },
    getConsentLabel(key) {
      const labels = {
        agreeAll: '전체 동의',
        agree1: '만 14세 이상입니다 (필수)',
        agree2: '이용약관 (필수)',
        agree3: '개인정보수집 및 이용동의 (필수)',
        agreeMarketUsage: '개인정보 마케팅 활용 동의 (선택)',
        agreePromotion: '이벤트, 쿠폰, 특가 알림 메일 및 SMS 등 수신 (선택)',
      };
      return labels[key] || '';
    }
  }
};
</script>

<style>
body {
    background-color: #f9f9f9;
    margin: 0;
    padding: 0;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    margin-bottom: 50px;
}

.signup-content {
  display: flex;
  width: 100%;
  min-height: 100vh;
  align-items: center;
  flex-direction: column;
}

.signup-container {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 100%;
    //max-width: 400px;
    text-align: center;
    margin-top: 20px; /* 이미지와 로고에 겹치지 않도록 여백 추가 */
    border: 1px solid #ccc; /* 보더 추가 */

}

.text-with-image-sign {
    display: flex;
    align-items: center;
    margin-bottom: 0px;
    text-align: center;
    top: 15px; /* 화면 상단에 정렬 */
    left: 10px; /* 화면 왼쪽에 정렬 */
    padding: 0px; /* 간격 조절 */
    z-index: 1000; /* 다른 요소들보다 위에 표시되도록 설정 */
    color: #494949;
    font-size: 14px;
    justify-content: center;
}

.text-with-image-sign img {
    width: 130px;
    height: auto;
    margin-right: 0px;
    border-radius: 20%;
    padding-left: 0px;
}

.signup-container h2 {
    color: #333;
}

.signup-container input {
    width: 100%;
    padding: 10px;
    margin-bottom: 0px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.signup-container button {
    width: 100%;
    padding: 10px;
    background-color: #18cc3c;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.signup-container button:hover {
    background-color: #00ab03;
}

.signup-content-container {
    text-align: center;
    margin-top: 20px;
}

.content-container a {
    color: #333;
    text-decoration: none;
    margin: 0 10px;
    font-size: 12px;
}

.sns-buttons-sign {
    margin-top: 20px;
}

.sns-buttons-sign a {
    display: inline-block;
    margin: 0 10px;
    padding: 10px;
    text-decoration: none;
    color: #fff;
    border-radius: 50%;
    cursor: pointer;
}

.sns-buttons-sign .facebook {
    background-color: #3b5998;
}

.sns-buttons-sign .kakao {
    background-color: #ffeb00;
}

.sns-buttons-sign .naver {
    background-color: #00c63b;
}

.confortLogin {
    font-size: 11px;
    color: #696969;
    margin-top: 0px;
    margin-bottom: -7px;
}

.sns-buttons-sign img {
    width: 18px;
    height: 18px;
    margin-right: 4px;
}

.insertEmail {
    text-align: left;
    margin-top: -30px;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertpassword {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertpassword2 {
    text-align: left;
    margin-bottom: 5px;
    font-size: 10px;
    color: #494949;
}
.insertcheck {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertnick {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertnick2 {
    text-align: left;
    margin-bottom: 5px;
    font-size: 10px;
    color: #494949;
}

.signupfont {
    text-align: center;
    font-size: 14px;
    color: #494949;
    margin-top: -10px;
    margin-bottom: -10px;
}

.line {
    margin-top: 20px;
    border: none;
    height: 1px;
    background-color: #c2c8cc;
}

.emailAuth {
    margin-top: 4px;
    padding: 0px;
}

.emailAuth button {
    width: 100%;
    padding: 10px;
    background-color: #ffbe0e;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease 0s; /* 호버 효과를 위한 transition 추가 */
}

.emailAuth button:hover {
    background-color: #99154e;
}

.consent-label {
    display: flex;
    align-items: center;
    text-align: left;
    margin-bottom: -10px;
    font-size: 15px;
    color: #494949;
}

.consent-options {
    border: 1px solid #ccc; /* Add border to wrap the consent options */
    padding: 10px;
}

.checkbox-label {
    display: flex;
    align-items: center;
    text-align: left;
}

.checkbox-wrapper {
    width: 20px; /* Set the width of the checkbox wrapper */
    height: 20px; /* Set the height of the checkbox wrapper */
    border: 1px solid #ccc; /* Add border to create a box */
    margin-right: 10px; /* Add margin to separate the box from text */
    display: flex;
    align-items: center;
    justify-content: center;
}

.consent-text {
    font-size: 11px;
    color: #494949;
}

.additional-consent {
    font-size: 9px;
    opacity: 0.7; /* 연하게 만들기 위한 투명도 조절 */
}
.loginsubmit{
  background-color: black;
  color: #fff;
  border: 1px solid black;
  transition: all 0.5s;
}
.loginsubmit:hover{
  background-color: #FFFFFF;
  color:black;
}
.insertname {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertage {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertgender {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

.insertaddress {
    text-align: left;
    margin-bottom: -15px;
    font-size: 15px;
    color: #494949;
}

#customerAddress {
    width: 395px;
    height: 100px;
}
</style>
