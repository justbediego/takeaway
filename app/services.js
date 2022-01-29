import axios from 'axios';
export const basePath = "http://192.168.0.160:2102/";
import i18n from "i18next";

type RequestInfo = {
    method: 'GET' | 'POST' | 'PATCH' | 'DELETE';
    data: any;
    headers: any;
    parent: 'user' | 'attachment';
    action: string;
}

type GetBasicInfoDto = {
    profilePictureKey: string;
    profilePictureId: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    phoneNumberCountryCode: string;
    email: string;
    username: string;
}

const callService = async ({data, headers, method, parent, action}: RequestInfo) => {
    try {
        const response = await axios({
            url: `${basePath}/${parent}/${action}`,
            method,
            headers,
            body: JSON.stringify(data)
        });
        return response.data;
    } catch (ex) {
        if(ex?.response?.status === 409 && ex?.response?.data?.type){
            // takeaway exception
            const {type, message, details} = ex.response.data;
            throw {
                type,
                message,
                details,
                translation: i18n.t(`exceptions.${type}`)
            }
        }else{
            const type = 'UnexpectedClientSideException';
            throw {
                type,
                message: ex?.message,
                translation: i18n.t(`exceptions.${type}`)
            }
        }
    }
}

export const authenticateEmail = () => {
//POST
}

export const authenticateUsername = () => {
//POST
}

export const changePassword = () => {
//PATCH
}

export const getBasicInfo = (): Promise<GetBasicInfoDto> => callService({
    method: "GET",
    parent: "user",
    action: 'getBasicInfo'
})

export const modifyAddress = async () => {
//PATCH
}

export const updateBasicInfo = () => {
//PATCH
}

export const updateEmail = () => {
//PATCH
}

export const updateProfilePicture = () => {
//PATCH
}

export const updateUsername = () => {
//PATCH
}
