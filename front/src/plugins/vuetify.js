import Vue from "vue";
import Vuetify from "vuetify/lib/framework";

Vue.use(Vuetify);

const vuetify = new Vuetify({
  theme: {
    themes: {
      light: {
        primary: '#0288d1',
        secondary: '#09DEB5',
        accent: '#FFD600',
        error: '#FF0000',
        background: '#F8F9FA'
      },
    },
  },
})

export default new Vuetify({});
