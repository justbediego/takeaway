import { createSlice } from '@reduxjs/toolkit';

export const basicInfoSlice = createSlice({
    name: 'basicInfo',
    initialState: {
        value: {},
    },
    reducers: {
        update: (state, action) => {
            state.value = action.payload
        },
    },
})

export const { update } = basicInfoSlice.actions

export default basicInfoSlice.reducer