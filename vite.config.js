import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://98.86.68.47:8081',
        changeOrigin: true,
        secure: false,
      },
    },
  },
})
