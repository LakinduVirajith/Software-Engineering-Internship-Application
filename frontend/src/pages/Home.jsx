/* eslint-disable react-hooks/exhaustive-deps */
import React from 'react'
import { useNavigate  } from 'react-router-dom';
import { Button, Flex, Grid, GridItem, Image, Text } from '@chakra-ui/react'
import logo from '../assets/logo.jpg';
import toast from 'react-hot-toast';
import { fetchPosts, updateLikes } from '../services/postService';
import { fetchUser } from '../services/userService';
import Profile from '../components/Profile';
import Post from '../components/Post';

function Home() {
  const [pageNumber, setPageNumber] = React.useState(0);
  const [sortBy, setSortBy] = React.useState('time');
  const [posts, setPosts] = React.useState([]);
  const [loading, setLoading] = React.useState(false);
  const pageSize = 10;
  const [profileData, setProfileData] = React.useState({
    fullName: 'Test User',
    userName: 'Test',
    profileImage: null,
  });

  const navigate = useNavigate();

  // EFFECT FOR FETCHING USER DETAILS ONLY ONCE
  React.useEffect(() => {
    const fetchUserProfile = async () => {
      const data = await fetchUser();
      if (data) {
        setProfileData({
          fullName: data.fullName,
          userName: data.userName,
          profileImage: data.profileImage,
        }); 
      }
    }

    fetchUserProfile();
  }, []);

  // EFFECT FOR FETCHING POSTS BASED ON PAGE NUMBER
  React.useEffect(() => {
    const loadPosts = async () => {
      setLoading(true);
      
      const data = await fetchPosts(pageNumber, pageSize, sortBy);
      if (data) {
        setPosts(data || []);   
      }

      setLoading(false);
    };

    loadPosts();
  }, [pageNumber, sortBy]);

  // HANDLE THE LIKE/UNLIKE ACTION
  const handleLike = async (postId, liked) => {
    const response = await updateLikes(postId);

    if (response) {
      setPosts((prevPosts) =>
        prevPosts.map((post) =>
          post.postId === postId
            ? { ...post, liked: !liked, likes: liked ? post.likes - 1 : post.likes + 1 }
            : post
        )
      );
    }
  };

  // TOGGLE BETTWEEN SORTING BY 'TIME' AND 'LIKES'
  const toggleSortBy = () => {
    setPageNumber(0);
    setSortBy((prevSortBy) => (prevSortBy === 'time' ? 'likes' : 'time'));
    toast("ℹ️ Sorting order updated! Posts will now be sorted by " + (sortBy === 'time' ? 'likes' : 'time'))
  };

  // HANDLE SCROLL EVENT TO LOAD MORE POSTS
  const handleScroll = (event) => {
    const { scrollTop, scrollHeight, clientHeight } = event.target;
    const bottomDistance = scrollHeight - scrollTop - clientHeight;
    const loadMoreThreshold = 10;
    if (bottomDistance <= loadMoreThreshold && !loading) {
        setPageNumber((prevPageNumber) => prevPageNumber + 1);
    }
  };

  // HANDLE LOGOUT
  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    document.cookie = 'refreshToken=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 UTC;';
    toast.success('Successfully logged out!');
    navigate('/login');
  };

  return (
    <>
      <Grid templateColumns='repeat(7, 1fr)' height='100vh' gap={6}>
        {/* SECTION 1*/}
        <GridItem colSpan={2} paddingY={4} display='flex' justifyContent='center'>
          <Image 
            src={logo}
            borderRadius='full'
            boxSize='230px'
            alt='logo' />
        </GridItem>
        {/* SECTION 2 - POSTS */}
        <GridItem colSpan={3} bg='gray.100' overflowY='auto' onScroll={handleScroll} maxHeight='100vh'>
          {posts.length > 0 ? (
            posts.map((post) => (
              <Post key={post.postId} post={post} onLike={handleLike} />
            ))
          ) : (
            <Text 
              fontSize='xl'
              fontWeight='medium'
              textAlign='center'
              pt={12}>
              No posts available
            </Text>
          )}
        </GridItem>
        {/* SECTION 3 - PROFILE */}
        <GridItem colSpan={2} paddingY={10} display='flex' flexDirection='column' justifyContent='space-between'>
          <Profile profileData={profileData} />
          <Flex flexDirection='column' alignItems='center'>
            <Button 
              colorScheme='blue'
              onClick={toggleSortBy}
              marginY={4}
              width={60}>
              Post Sorted by: {sortBy === 'time' ? 'Time' : 'Likes'}
            </Button>
            <Button colorScheme='blackAlpha' 
              width={60} 
              onClick={handleLogout} 
              bg='blackAlpha.800'
              color='white' 
              _hover={{bg: 'blackAlpha.900',}}
              _active={{bg: 'black',}}>
              Logout
            </Button>
          </Flex>
        </GridItem>
      </Grid>
    </>
  )
}

export default Home