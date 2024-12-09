import React from 'react';
import { Image, Text, Flex, GridItem } from '@chakra-ui/react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp } from '@fortawesome/free-solid-svg-icons';
import dayjs from 'dayjs';

function Post({ post, onLike }) {
  return (
    <GridItem key={post.postId} colSpan={3} bg='white' mx={10} my={8} boxShadow='lg' borderRadius='lg'>
        <Image src={`data:image/jpeg;base64,${post.image}`} alt='post' borderRadius='lg' width='full' />
        <Flex justifyContent='space-between' py={4} px={5} alignItems='center'>
            <Flex gap={2} alignItems='center' onClick={() => onLike(post.postId, post.liked)}>
                <FontAwesomeIcon 
                    size='xl' 
                    icon={faThumbsUp} 
                    color={post.liked ? '#4052d6' : 'gray'} />
                <Text fontSize='xl' fontWeight='medium'>
                    {post.likes || 0}
                </Text>
            </Flex>

            <Text 
            fontSize='xl'
            fontWeight='medium'>
                {post.userName}
            </Text>
            
            <Text 
            fontSize='xl'
            fontWeight='medium'>
            {dayjs(post.modifiedDate).format('MMM D, YYYY')}
            </Text>
        </Flex>
    </GridItem>
  );
}

export default Post;
