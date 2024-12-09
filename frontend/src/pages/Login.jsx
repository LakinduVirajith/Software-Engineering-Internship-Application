import React from 'react'
import { useNavigate  } from 'react-router-dom';
import { Button, Card, CardBody, Center, FormControl, Input, InputGroup, InputRightElement, Text } from '@chakra-ui/react'
import { Link as ReactRouterLink } from 'react-router-dom'
import { Link as ChakraLink } from '@chakra-ui/react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import toast from 'react-hot-toast'
import { useForm } from 'react-hook-form';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { login } from '../services/authService';

const validationSchema = Yup.object({
  userName: Yup.string().required('User Name is required.'),
  password: Yup.string().required('Password is required.'),
});

function Login() {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue
  } = useForm({
    resolver: yupResolver(validationSchema)
  });

  const [showPassword, setShowPassword] = React.useState(false);
  const handleClickPassword = () => setShowPassword(!showPassword);

  const onSubmit = async (data) => {
    const response = await login(data);
    if(response){
      toast.success(response.status + ": " + response.data.message)
      
      const { accessToken, refreshToken } = response.data;
      localStorage.setItem('accessToken', accessToken);
      document.cookie = `refreshToken=${refreshToken}; Path=/; HttpOnly; Secure`;

      resetForm();
      navigate('/home');
    }
  }

  const resetForm = () => {
    setValue('userName', '');
    setValue('password', '');
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

            <form onSubmit={handleSubmit(onSubmit)}>
              <FormControl marginBottom={4}>
                <Input 
                  type='text'
                  placeholder='User Name'
                  {...register('userName')}
                  isInvalid={!!errors.userName} />
                  <Text color='red.500' fontSize="sm" display='flex' pl={4} pt={1}>{errors.userName?.message}</Text>
              </FormControl>

              <FormControl marginBottom={4}>
                <InputGroup size='md'>
                  <Input 
                    pr="4.5rem"
                    placeholder="Password"
                    type={showPassword ? 'text' : 'password'}
                    {...register('password')}
                    isInvalid={!!errors.password} />
                  <InputRightElement width='4.5rem'>
                    <FontAwesomeIcon 
                      icon={showPassword ? faEyeSlash : faEye}
                      onClick={handleClickPassword}/>
                  </InputRightElement>
                </InputGroup>
                <Text color='red.500' fontSize="sm" display='flex' pl={4} pt={1}>{errors.password?.message}</Text>
              </FormControl>

              <Button 
                colorScheme='blue'
                size='md'
                width='full'
                type='submit'
                onClick={handleSubmit}>
                Login
              </Button>
            </form>

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