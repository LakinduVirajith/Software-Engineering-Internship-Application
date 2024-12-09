import React from 'react';
import { Image, Text, Flex } from '@chakra-ui/react';
import profile from '../assets/profile.png';

function Profile({ profileData }) {
  return (
    <Flex flexDirection="column" alignItems="center">
      <Image 
        src={profileData.profileImage ? `data:image/jpeg;base64,${profileData.profileImage}` : profile}
        borderRadius="full"
        boxSize="150px"
        marginBottom={4}
        alt="profile"
      />
      <Text fontSize="xl" fontWeight="medium">
        {profileData.fullName}
      </Text>
      <Text fontSize="md" fontWeight="normal">
        {profileData.userName}
      </Text>
    </Flex>
  );
}

export default Profile;
