import authHeader, { BASE_URL, HTTP } from "../Http";

const getAll = async (user) => {
  return HTTP.get(BASE_URL + "/users", {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const getUser = async (user, id) => {
  return HTTP.get(BASE_URL + "/users/" + id, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const addUser = async (user, data) => {
  return HTTP.post(BASE_URL + "/users", data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const searchUser = async (user, data) => {
  return HTTP.get(BASE_URL + "/users/search/" + data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const deleteUser = async (user, data) => {
  return HTTP.delete(BASE_URL + "/users/" + data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const updateUser = async (user, id, data) => {
  return HTTP.put(BASE_URL + "/users/" + id, data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const getDoctorsAvailable = async (user, data) => {
  return HTTP.get(BASE_URL + "/users/available/" + data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const getConsultationsForUser = async (user) => {
  return HTTP.get(BASE_URL + "/users/" + user.id + "/consultations", {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

export const UsersService = {
  getAll,
  getUser,
  addUser,
  searchUser,
  deleteUser,
  updateUser,
  getDoctorsAvailable,
  getConsultationsForUser,
};
