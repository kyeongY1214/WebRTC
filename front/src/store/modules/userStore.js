import axios from 'axios';
import Cookies from 'js-cookie';
import jwt_decode from 'jwt-decode';

import http from '@/util/http-common';


const getDefaultState = () => {
  return {
    accessToken: null,
    loginStatus: false,
    lang: 'en',
    userId: null,
    nickname: null,
  }
}

const userStore = {
  namespaced: true,
  state: getDefaultState(),
  mutations: {
    LOGIN(state, payload) {
      state.accessToken = payload.accessToken;
      state.loginStatus = true;
      state.lang = payload.lang;
      state.userId = payload.userId;
      state.nickname = payload.nickname;
    },
    LOGOUT(state) {
      Object.assign(state, getDefaultState());
    },
    SET_ACCESS_TOKEN(state) {
      state.accessToken = Cookies.get('accessToken');
    },
  },
  actions: {
    requestRegister(context, payload) {
      let body = payload
      return http.post('/users', body)
    },
    requestLogin(context, payload) {
      let body = payload
      return http.post('/auth/login', body)
    },
    login(context, accessToken) {
      const decoded = jwt_decode(accessToken);
      const userInfo = decoded.userinfo;
      const payload = {
        userId: Number(userInfo.id),
        nickname: userInfo.nickname,
        lang: userInfo.lang,
        accessToken
      }
      console.log(decoded);
      context.commit('LOGIN', payload)
    },
    logout(context) {
      context.commit('LOGOUT')
      axios.defaults.headers.common['Authorization'] = undefined
    },
    // NOTE: 유저 정보 요청
    requestUserInfo(context, userId) {
      return http.get('/users/' + userId)
    },
    // NOTE: 만난 사람들 요청
    requestUserHistory(context, userId) {
      return http.get()
    },
    // NOTE: 작성한 리뷰 요청
    requestGiveReviews(context, userId) {
      return http.get('/review/from/' + userId)
    },
    // NOTE: 받은 리뷰 요청
    requestReceivedReviews(context, userId) {
      return http.get('/review/to/' + userId)
    },
  },
  getters: {
    getAccessToken(state) {
      return state.accessToken;
    },
    getLoginStatus(state) {
      return state.loginStatus;
    },
    getLocale(state) {
      return state.lang;
    },
    getUserId(state) {
      return state.userId;
    },
    getNickName(state) {
      return state.nickname;
    }
  },
}
export default userStore