  import { shallowMount } from '@vue/test-utils'
  import LoginPage from '@/page/LoginPage.vue'
  import { useAdminStore } from '@/stores/useAdminStore'

  jest.mock('@/stores/useAdminStore')

  describe('LoginPage.vue', () => {

    beforeEach(() => {
      useAdminStore.mockClear()
    })

    it('로그인 폼 입력 렌더링에 대한 테스트', () => {
      const wrapper = shallowMount(LoginPage)
      
      // 이메일 입력란이 존재하는지 확인
      const emailInput = wrapper.find('input[type="text"][id="email"]')
      expect(emailInput.exists()).toBe(true)

      // 비밀번호 입력란이 존재하는지 확인
      const passwordInput = wrapper.find('input[type="password"][id="password"]')
      expect(passwordInput.exists()).toBe(true)

      // 로그인 버튼이 존재하는지 확인
      const loginButton = wrapper.find('button[type="button"]')
      expect(loginButton.exists()).toBe(true)
    })

    it('폼이 제출되면 로그인 메서드 호출됨', async () => {

      const loginMethod = jest.fn()
      useAdminStore.mockReturnValue({ login: loginMethod })

      const wrapper = shallowMount(LoginPage)

      // 이메일과 비밀번호 입력
      wrapper.setData({ loginForm: { adminEmail: 'test@example.com', adminPwd: 'password123' } })

      // 로그인 폼을 제출
      await wrapper.find('form').trigger('submit.prevent')

      // login 메서드가 호출되었는지 확인
      expect(loginMethod).toHaveBeenCalled()
    })

    it('API 실패 시, 에러 메시지를 보여준다.', async () => {

      const loginMethod = jest.fn().mockRejectedValue(new Error('로그인 실패'))
      useAdminStore.mockReturnValue({ login: loginMethod })

      // window.alert를 모킹 함수로 대체
      const mockAlert = jest.spyOn(window, 'alert').mockImplementation(() => {});

      const wrapper = shallowMount(LoginPage)

      // 이메일과 비밀번호 입력
      wrapper.setData({ loginForm: { adminEmail: 'test@example.com', adminPwd: 'invalidpw' } })

      // 로그인 폼을 제출
      await wrapper.find('form').trigger('submit.prevent')

      // login 메서드가 호출되었는지 확인
      expect(loginMethod).toHaveBeenCalled()

      // 로그인 실패 시 호출된 alert 함수가 정확히 호출되었는지 확인
      expect(mockAlert).toHaveBeenCalledWith('로그인 실패');
    })

  })
