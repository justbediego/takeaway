import {configureStore} from '@reduxjs/toolkit';
import basicInfoReducer from "./basicInfoSlice";

export default configureStore({
    reducer: {
        basicInfo: basicInfoReducer,
    },
})