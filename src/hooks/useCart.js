import { useEffect, useState } from 'react'
import { getAllProducts } from '../services/productService'

export const useCart = () => {

    const initialCart = () => { //inicializo el carrito
        try {
            const localStorageCart = localStorage.getItem('cart')
            return localStorageCart ? JSON.parse(localStorageCart) : []
        } catch {
            return []
        }
    }

    const [data, setData] = useState([])
    const [cart, setCart] = useState(initialCart)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    //defino el máximo y mínimo de items que utilizará el carrito
    const MAX_ITEMS = 8
    const MIN_ITEMS = 1

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const products = await getAllProducts();
                setData(products);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    useEffect(() => {
        localStorage.setItem('cart', JSON.stringify(cart))
    }, [cart])

    //función para agregar productos al carrito
    function addToCart(item) {
        const itemExists = cart.findIndex(p => p.id === item.id)
        if (itemExists >= 0) { //si el item existe en el carrito
            if (cart[itemExists].quantity >= MAX_ITEMS) return

            const updateCart = [...cart]
            updateCart[itemExists] = {
                ...updateCart[itemExists],
                quantity: updateCart[itemExists].quantity + 1
            }
            setCart(updateCart)
        } else { //si el item no existe en el carrito
            const itemForCart = { ...item, quantity: 1 }
            setCart(prev => [...prev, itemForCart])
        }
    }

    function removeFromCart(id) {
        setCart(prevCart => prevCart.filter(producto => producto.id !== id))
    }

    function decreaseQuantity(id) {
        const updatedCart = cart.map(item => {
            if (item.id === id && item.quantity > MIN_ITEMS) {
                return {
                    ...item,
                    quantity: item.quantity - 1
                }
            }
            // Devuelve una copia del item para asegurar la inmutabilidad del array
            return { ...item };
        });
        setCart(updatedCart)
    }

    function increaseQuantity(id) {
        const updatedCart = cart.map(item => {
            if (item.id === id && item.quantity < MAX_ITEMS) {
                return {
                    ...item,
                    quantity: item.quantity + 1
                }
            }
            // Devuelve una copia del item para asegurar la inmutabilidad del array
            return { ...item };
        });
        setCart(updatedCart)
    }

    function clearCart() {
        setCart([])
    }

    return {
        data,
        cart,
        loading,
        error,
        addToCart,
        removeFromCart,
        decreaseQuantity,
        increaseQuantity,
        clearCart
    }
}
