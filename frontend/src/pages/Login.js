import React from 'react'
import { useNavigate  } from 'react-router-dom';
import { Button, Card, CardBody, Center, FormControl, Input, InputGroup, InputRightElement, Text } from '@chakra-ui/react'
import { Link as ReactRouterLink } from 'react-router-dom'
import { Link as ChakraLink } from '@chakra-ui/react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import toast from 'react-hot-toast'
import { login } from '../services/authService';

function Login() {
  const [userName, setUserName] = React.useState(null);
  const [password, setPassword] = React.useState(null);

  const [showPassword, setShowPassword] = React.useState(false);
  const handleClickPassword = () => setShowPassword(!showPassword);

  const navigate = useNavigate();

  const handleSubmit = async () => {
    const response = await login({ userName, password });
    if(response){
      toast.success(response.status + ": " + response.data.message)
      
      const { accessToken, refreshToken } = response.data;
      localStorage.setItem('accessToken', accessToken);
      document.cookie = `refreshToken=${refreshToken}; Path=/; HttpOnly; Secure`;

      resetForm();
      setTimeout(() => {
        navigate('/home');
      }, 1000);
    }
  }

  const resetForm = () => {
    setUserName(null);
    setPassword(null);
  };

  return (
    <>
      <Center height="100vh" bg="gray.100">
        <Card align='center' width="50vh" boxShadow="xl" borderRadius="2xl" padding="1">
          <CardBody align='center'>
            <Text 
              fontSize='4xl'
              fontWeight="bold"
              marginBottom={4}>
                WELCOME BACK
            </Text>

            <FormControl onSubmit={(e) => e.preventDefault()}>
              <Input 
                type='text'
                placeholder='Full Name'
                marginBottom={4}
                value={userName}
                onChange={(e) => setUserName(e.target.value)}/>

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
            </FormControl>

            <Button 
              colorScheme='blue'
              size='md'
              width='full'
              onClick={handleSubmit}>
              Login
            </Button>

            <Text marginTop={4}>
              Don't have an account yet?{' '}
              <ChakraLink 
                fontWeight="medium" 
                as={ReactRouterLink} 
                to='/sign-up'>
                  Create
              </ChakraLink>
            </Text>
          </CardBody>
        </Card>
      </Center>
    </>
  )
}

export default Login