import authHeader, { BASE_URL, HTTP } from "../Http";

const getAll = async (user) => {
  return HTTP.get(BASE_URL + "/consultations", {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const getConsultation = async (user, id) => {
  return HTTP.get(BASE_URL + "/consultations/" + id, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const addConsultation = async (user, data) => {
  return HTTP.post(BASE_URL + "/consultations", data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const deleteConsultation = async (user, data) => {
  return HTTP.delete(BASE_URL + "/consultations/" + data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const updateConsultation = async (user, id, data) => {
  return HTTP.put(BASE_URL + "/consultations/" + id, data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

const updateDescription = async (user, id, data) => {
  return HTTP.patch(BASE_URL + "/consultations/" + id, data, {
    headers: authHeader(user),
  }).then((response) => {
    return response.data;
  });
};

export const ConsultationsService = {
  getAll,
  getConsultation,
  addConsultation,
  deleteConsultation,
  updateConsultation,
  updateDescription,
};
