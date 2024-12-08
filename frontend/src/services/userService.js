import axios from 'axios';
import toast from 'react-hot-toast';

const checkConfig = (navigate) => {
    const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
    const accessToken = localStorage.getItem('accessToken');

    if (!API_BASE_URL) {
      console.error('API Base URL is not configured!');
      toast.error('Internal configuration error. Please contact support.');
      navigate('/login');
      return false;
    }
  
    if (!accessToken) {
      console.error('Missing access token!');
      toast.error('Authentication error. Please login again.');
      navigate('/login');
      return false;
    }
  
    return {API_BASE_URL, accessToken};
};

// SERVICE TO FETCH USER DETAILS
export const fetchUser = async (navigate) => {
    const config = checkConfig(navigate);
    if (!config) {
        return;
    }

    const { API_BASE_URL, accessToken } = config;
    try {    
        const response = await axios.get(`${API_BASE_URL}/user/profile`,
            {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error during fetching user:', error);
        toast.error('Something went wrong. Please try again later.');
    }
};