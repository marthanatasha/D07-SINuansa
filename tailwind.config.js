/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      backgroundImage: {
        'login': "url('../resources/static/images/login-bg.png')",
      }
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}
