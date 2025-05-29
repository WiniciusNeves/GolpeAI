// src/components/AuthCard.tsx
'use client'

import { useState } from 'react'
import { motion, AnimatePresence } from 'framer-motion'
import LoginForm from '../components/LoginForm'
import RegisterForm from '../components/RegisterForm'

export default function AuthCard() {
  const [isLogin, setIsLogin] = useState(true)

  return (
    <div className="relative w-[360px] min-h-[460px] bg-white shadow-xl rounded-xl overflow-hidden p-6">
      <div className="flex justify-between mb-6">
        <button
          onClick={() => setIsLogin(true)}
          className={`w-1/2 py-2 text-center font-semibold transition ${
            isLogin ? 'text-blue-600 border-b-2 border-blue-600' : 'text-gray-400'
          }`}
        >
          Entrar
        </button>
        <button
          onClick={() => setIsLogin(false)}
          className={`w-1/2 py-2 text-center font-semibold transition ${
            !isLogin ? 'text-blue-600 border-b-2 border-blue-600' : 'text-gray-400'
          }`}
        >
          Cadastrar
        </button>
      </div>

      <div className="relative h-[350px]">
        <AnimatePresence mode="wait">
          {isLogin ? (
            <motion.div
              key="login"
              initial={{ opacity: 0, x: -50 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: 50 }}
              transition={{ duration: 0.3 }}
              className="absolute inset-0"
            >
              <LoginForm />
            </motion.div>
          ) : (
            <motion.div
              key="register"
              initial={{ opacity: 0, x: 50 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -50 }}
              transition={{ duration: 0.3 }}
              className="absolute inset-0"
            >
              <RegisterForm />
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </div>
  )
}
