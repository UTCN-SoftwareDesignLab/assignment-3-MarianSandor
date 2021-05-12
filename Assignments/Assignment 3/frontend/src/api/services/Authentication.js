import { BASE_URL, HTTP } from "../Http";

const login = async (data) => {
  return HTTP.post(BASE_URL + "/auth/sign-in", data).then((response) => {
    return response.data;
  });
};

const register = async (data) => {
  return HTTP.post(BASE_URL + "/auth/sign-up", data);
};

export const AuthenticationService = {
  login,
  register,
};
