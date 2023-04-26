/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      backgroundImage: {
        'login': "url('../images/login-bg.png')",
      }
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}
