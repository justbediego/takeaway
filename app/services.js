import axios from 'axios';
import i18n from "i18next";

export const basePath = "http://192.168.1.198:8080";

type RequestInfo = {
    method: 'GET' | 'POST' | 'PATCH' | 'DELETE';
    data: any;
    headers: any;
    parent: 'user' | 'attachment' | 'authentication' | 'guest' | 'user-item';
    action: string;
}

type GetBasicInfoDto = {
    profilePictureKey: string;
    profilePictureId: string;
    profilePictureOriginalKey: string;
    profilePictureOriginalId: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    phoneNumberCountryCode: string;
    email: string;
    username: string;
}

type CountryCodeDto = {
    countryName: string;
    countryCode: string;
}

type GetCountryCodesDto = {
    countries: CountryCodeDto[];
}

type UpdateBasicInfoDto = {
    firstName: string;
    lastName: string;
    phoneNumber: string;
    phoneNumberCountryCode: string;
}

const handleServiceException = async (callMethod: Promise) => {
    try {
        return (await callMethod())?.data;
    } catch (ex) {
        if (ex?.response?.status === 409 && ex?.response?.data?.type) {
            // takeaway exception
            const {type, message, details} = ex.response.data;
            throw {
                type,
                message,
                details,
                translation: i18n.t(`exceptions.${type}`)
            }
        } else {
            const type = 'UnexpectedClientSideException';
            throw {
                type,
                message: ex?.message,
                translation: i18n.t(`exceptions.${type}`)
            }
        }
    }
}

const callFileService = ({data, method, parent, action}: RequestInfo) =>
    handleServiceException(() => fetch(`${basePath}/${parent}/${action}`, {
        method,
        headers: {
            "Content-Type": "multipart/form-data"
        },
        body: data
    }))

const callJsonService = ({data, headers, method, parent, action}: RequestInfo) =>
    handleServiceException(() => axios({
        url: `${basePath}/${parent}/${action}`,
        method,
        headers: {
            'Content-Type': 'application/json',
            ...headers
        },
        data: JSON.stringify(data)
    }))

export const authenticateEmail = () => {
//POST
}

export const authenticateUsername = () => {
//POST
}

export const changePassword = () => {
//PATCH
}

export const getBasicInfo = (): Promise<GetBasicInfoDto> => callJsonService({
    method: "GET",
    parent: "user",
    action: 'getBasicInfo'
})

export const modifyAddress = async () => {
//PATCH
}

export const updateBasicInfo = (data: UpdateBasicInfoDto): Promise => callJsonService({
    method: "PATCH",
    parent: "user",
    action: 'updateBasicInfo',
    data
})

export const updateEmail = () => {
//PATCH
}

export const updateProfilePicture = (data): Promise => callFileService({
    method: "PATCH",
    parent: "user",
    action: "updateProfilePicture",
    data
})

export const updateUsername = () => {
//PATCH
}

export const getCountryCodes = (): Promise<GetCountryCodesDto> => callJsonService({
    method: "GET",
    parent: "guest",
    action: 'getCountryCodes'
})

export const deleteProfilePicture = (): Promise => callJsonService({
    method: "DELETE",
    parent: "user",
    action: 'deleteProfilePicture'
})

export const getAttachmentLink = (attachmentId, attachmentKey): Promise => callJsonService({
    method: "GET",
    parent: "attachment",
    action: `getAttachmentLink/${attachmentId}/${attachmentKey}`
})