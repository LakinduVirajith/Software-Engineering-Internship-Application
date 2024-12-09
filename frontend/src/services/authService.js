import toast from 'react-hot-toast';
import axiosInstance from '../api/axiosInstance';

// SERVICE TO SIGN-UP
export const signUp = async (userData) => {
  try {
    const response = await axiosInstance.post('/user/sign-up', userData);
    return response;
  } catch (error) {
    if (error.response) {  
        toast.error(error.response.status + ": " + error.response.data)
    } else {
        console.error("Error during signup:", error);
        toast.error("Something went wrong. Please try again later.");
    }
  }
};

// SERVICE TO LOGIN
export const login = async (credentials) => {
  try {
    const response = await axiosInstance.post('/user/login', credentials);
    return response;
  } catch (error) {
    if (error.response) {
        if (error.response.data) {
          toast.error(`${error.response.status}: ${error.response.data}`);
        } else {
          toast.error(`${error.response.status}: Invalid username or password`);
        }
    } else {
        console.error("Error during login:", error);
        toast.error("Something went wrong. Please try again later.");
    }
  }
};
