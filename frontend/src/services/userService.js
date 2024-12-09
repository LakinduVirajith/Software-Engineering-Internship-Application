import toast from 'react-hot-toast';
import axiosInstance from '../api/axiosInstance';

// SERVICE TO FETCH USER DETAILS
export const fetchUser = async (navigate) => {
    try {    
        const response = await axiosInstance.get('user/profile');
        return response.data;
    } catch (error) {
        console.error('Error during fetching user:', error);
        toast.error('Something went wrong. Please try again later.');
    }
};