import { useEffect, useState, useMemo } from 'react'
import db from '../data/db.js' //importo base de datos

export const useCart = () => {

    const initialCart = () => { //inicializo el carrito

        // CAMBIO: envuelvo el parseo del localStorage en try/catch para evitar que se rompa
        // si el JSON está corrupto o contiene algo no parseable.
        // Razón: robustez del estado inicial del carrito.
        try {
            const localStorageCart = localStorage.getItem('cart')
            return localStorageCart ? JSON.parse(localStorageCart) : []
        } catch {
            return []
        }
    }

    const [data] = useState(db)
    const [cart, setCart] = useState(initialCart)

    //defino el máximo y mínimo de items que utilizará el carrito
    const MAX_ITEMS = 8
    const MIN_ITEMS = 1

    useEffect(() => {
        localStorage.setItem('cart', JSON.stringify(cart))
    }, [cart])

    //función para agregar productos al carrito
    function addToCart(item){
        // CAMBIO: renombro la variable del findIndex para no sombrear "data" (tu estado).
        // Razón: claridad. (Funciona igual; antes usaba "data" como nombre de parámetro.)
        const itemExists = cart.findIndex(p => p.id === item.id)
        if (itemExists >= 0) { //si el item existe en el carrito
            if (cart [itemExists].quantity >= MAX_ITEMS) return

            // CAMBIO: actualizo de forma inmutable el objeto dentro del array.
            // Razón: evitar mutaciones directas que pueden romper renders y contaminar referencias.
            const updateCart = [...cart]
            updateCart[itemExists] = {
                ...updateCart[itemExists],
                quantity: updateCart[itemExists].quantity + 1
            }
            setCart(updateCart)
        }else{ //si el item no existe en el carrito
            // CAMBIO: no mutar el objeto "item" original (venía sin quantity).
            // En su lugar, clono y agrego quantity.
            // Razón: mantener inmutabilidad y no “contaminar” la fuente (data).
            const itemForCart = { ...item, quantity: 1 }
            setCart(prev => [...prev, itemForCart])
        }
    }

    function removeFromCart(id) {
        setCart(prevCart => prevCart.filter(producto => producto.id !== id))
    }

    function decreaseQuantity(id) {
        const updatedCart = cart.map(item => {
            if(item.id === id && item.quantity > MIN_ITEMS) {
                return {
                    ...item,
                    quantity: item.quantity - 1
                }
            }
            // Devuelve una copia del item para asegurar la inmutabilidad del array
            return {...item}; 
        });
        setCart(updatedCart)
    }

    function increaseQuantity(id) {
        const updatedCart = cart.map(item => {
            if(item.id === id && item.quantity < MAX_ITEMS) {
                return {
                    ...item,
                    quantity: item.quantity + 1
                }
            }
            // Devuelve una copia del item para asegurar la inmutabilidad del array
            return {...item};
        });
        setCart(updatedCart)
    }

    function clearCart() {
        setCart([])
    }

    return {
        data,
        cart,
        addToCart,
        removeFromCart,
        decreaseQuantity,
        increaseQuantity,
        clearCart
    }
}
