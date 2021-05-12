import authHeader, { BASE_URL, HTTP } from "../Http";

const getAll = async (user) => {
  return HTTP.get(BASE_URL + "/patients", { headers: authHeader(user) }).then(
    (response) => {
      return response.data;
    }
  );
};

const getPatient = async (user, id) => {
  return HTTP.get(BASE_URL + "/patients/" + id, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const addPatient = async (user, data) => {
  return HTTP.post(BASE_URL + "/patients", data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const searchPatient = async (user, data) => {
  return HTTP.get(BASE_URL + "/patients/search/" + data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const deletePatient = async (user, data) => {
  return HTTP.delete(BASE_URL + "/patients/" + data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const updatePatient = async (user, id, data) => {
  return HTTP.put(BASE_URL + "/patients/" + id, data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const getConsultationsForPatient = async (user, id) => {
  return HTTP.get(BASE_URL + "/patients/" + id + "/consultations", {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

export const PatientsService = {
  getAll,
  getPatient,
  addPatient,
  searchPatient,
  deletePatient,
  updatePatient,
  getConsultationsForPatient,
};
