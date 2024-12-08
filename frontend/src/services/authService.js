import axios from 'axios';
import toast from 'react-hot-toast';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

// SERVICE TO SIGN-UP
export const signUp = async (userData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/user/sign-up`, userData, {
      headers: { 'Content-Type': 'application/json' },
    });
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
    const response = await axios.post(`${API_BASE_URL}/user/login`, credentials, {
      headers: { 'Content-Type': 'application/json' },
    });
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
