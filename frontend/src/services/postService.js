import toast from 'react-hot-toast';
import axiosInstance from '../api/axiosInstance';

// SERVICE TO FETCH POST
export const fetchPosts = async (pageNumber, pageSize, sortBy) => {
    try {
        const response = await axiosInstance.get('/post/all', {
            params: {
                page: pageNumber,
                size: pageSize,
                sortBy: sortBy,
            },
        });
        return response.data;
    } catch (error) {
        if (error.response.data) {
            toast.error(`${error.response.status}: ${error.response.data}`);
        } else {
            console.error('Error during fetching posts:', error);
            toast.error('Something went wrong. Please try again later.');
        }
  }
};

// SERVICE TO UPDATE LIKES
export const updateLikes = async (postId) => {
    try {    
        const response = await axiosInstance.put(`/post/like/${postId}`);
        return response.data;
    } catch (error) {
        console.error('Error during like action:', error);
        toast.error('Something went wrong. Please try again later.');
    }
};
