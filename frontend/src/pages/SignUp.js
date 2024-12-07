import React from 'react'
import { useNavigate  } from 'react-router-dom';
import { Button, Card, CardBody, Center, FormControl, Input, InputGroup, InputRightElement, Text } from '@chakra-ui/react'
import { Link as ReactRouterLink } from 'react-router-dom'
import { Link as ChakraLink } from '@chakra-ui/react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import toast from 'react-hot-toast'

function SignUp() {
  const [fullName, setFullName] = React.useState(null);
  const [userName, setUserName] = React.useState(null);
  const [email, setEmail] = React.useState(null);
  const [password, setPassword] = React.useState(null);
  const [rePassword, setRePassword] = React.useState(null);

  const [showPassword, setShowPassword] = React.useState(false);
  const [showRePassword, setShowRePassword] = React.useState(false);
  const handleClickPassword = () => setShowPassword(!showPassword);
  const handleClickRePassword = () => setShowRePassword(!showRePassword);

  const navigate = useNavigate();

  const handleSubmit = async () => {
    if (password !== rePassword) {
      toast.error("400: Passwords do not match!");
      return;
    }

    const userData = {
      fullName,
      userName,
      email,
      password,
    };

    try {
      const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/user/sign-up`, userData, {
        headers: {
          'Content-Type': 'application/json',
        }
      });

      if (response.status === 201) {
        toast.success(response.status + ": " + response.data)
        resetForm();
        navigate('/login');
      }
    } catch (error) {
      console.error("Error during signup:", error);
      if (error.response) {  
        toast.error(error.response.status + ": " + error.response.data)
      } else {
        toast.error("Something went wrong. Please try again later.");
      }
    }
  }

  const resetForm = () => {
    setFullName('');
    setUserName('');
    setEmail('');
    setPassword('');
    setRePassword('');
  };

  return (
    <>
      <Center height="100vh" bg="gray.100">
        <Card align='center' width="50vh" boxShadow="xl" borderRadius="2xl" padding="1">
          <CardBody align='center'>
            <Text fontSize='4xl' fontWeight="bold" marginBottom={4}>SIGN UP</Text>

            <FormControl onSubmit={(e) => e.preventDefault()}>
              <Input 
                type='text'
                placeholder='Full Name'
                marginBottom={4}
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}/>

              <Input 
                type='text'
                placeholder='User Name'
                marginBottom={4}
                value={userName}
                onChange={(e) => setUserName(e.target.value)}/>

              <Input 
                type='email'
                placeholder='Email address'
                marginBottom={4}
                value={email}
                onChange={(e) => setEmail(e.target.value)}/>

              <InputGroup size='md' marginBottom={4}>
                <Input 
                  pr="4.5rem"
                  placeholder="Password"
                  type={showPassword ? 'text' : 'password'}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}/>
                <InputRightElement width='4.5rem'>
                  <FontAwesomeIcon 
                    icon={showPassword ? faEyeSlash : faEye}
                    onClick={handleClickPassword}/>
                </InputRightElement>
              </InputGroup>

              <InputGroup size='md' marginBottom={4}>
                <Input 
                  pr="4.5rem"
                  placeholder="Confirm Password"
                  type={showRePassword ? 'text' : 'password'}
                  value={rePassword}
                  onChange={(e) => setRePassword(e.target.value)}/>
                <InputRightElement width='4.5rem'>
                  <FontAwesomeIcon 
                    icon={showRePassword ? faEyeSlash : faEye} 
                    onClick={handleClickRePassword}/>
                </InputRightElement>
              </InputGroup>
            </FormControl>

            <Button 
              colorScheme='red'
              size='md'
              width='full'
              onClick={handleSubmit}>
              Sign Up
            </Button>

            <Text marginTop={4}>
              Already have an account?{' '}
              <ChakraLink fontWeight="medium" as={ReactRouterLink} to='/login'>
                Login
              </ChakraLink>
            </Text>
          </CardBody>
        </Card>
      </Center>
    </>
  )
}

export default SignUp