import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';
import {Picker, Text, TextInput, View} from '../components/Themed';
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";

import {Picker as DefaultPicker} from '@react-native-picker/picker'
import {getBasicInfo, getCountryCodes} from "../services";
import {useDispatch, useSelector} from "react-redux";
import {update as updateBasicInfoSlice} from "../store/basicInfoSlice";
import {Formik} from "formik";


const LabeledInput = ({label, value}) => {
    return (
        <View style={styles.labeledInput}>
            <Text style={styles.inputLabel}>{label}</Text>
            <TextInput style={styles.textInput}>{value}</TextInput>
        </View>
    );
}

export default function EditProfileScreen() {
    const {t} = useTranslation();
    const [countryCodes, setCountryCodes] = useState([]);
    const basicInfo = useSelector((state) => state.basicInfo.value);
    const dispatch = useDispatch();


    const refreshBasicInfo = async () => {
        try {
            const response = await getBasicInfo();
            await dispatch(updateBasicInfoSlice(response));
        } catch (e) {
            // todo
            console.log(e.translation);
        }
    }

    const updateCountryCodes = async () => {
        try {
            const response = await getCountryCodes();
            setCountryCodes(response
                ?.countries
                ?.sort((c1, c2) => c1?.countryName?.localeCompare(c2?.countryName))
            );
        } catch (e) {
            // todo
            console.log(e.translation);
        }
    }

    useEffect(() => {
        refreshBasicInfo();
        updateCountryCodes();
    }, [])

    return (
        <Formik initialValues={basicInfo}>
            {({handleChange, handleBlur, handleSubmit, values}) => (
                <View style={styles.container}>
                    <StatusBar style={Platform.OS === 'ios' ? 'light' : 'auto'}/>
                    <LabeledInput
                        label={t('labelFirstName')}
                        value={values.firstName}
                        onChangeText={handleChange('firstName')}
                        onBlur={handleBlur('firstName')}
                    />
                    <LabeledInput
                        label={t('labelLastName')}
                        value={values.lastName}
                        onChangeText={handleChange('lastName')}
                        onBlur={handleBlur('lastName')}
                    />
                    <View style={styles.labeledInput}>
                        <Text style={styles.inputLabel}>{t('labelPhoneNumber')}</Text>
                        <View style={styles.phoneNumberView}>
                            <Picker
                                mode="dialog"
                                selectedValue={values.phoneNumberCountryCode}
                                onValueChange={handleChange('phoneNumberCountryCode')}
                                style={[styles.textInput, styles.textCountryCode]}>
                                {countryCodes && countryCodes.map((value, index) =>
                                    <DefaultPicker.Item label={`${value.countryCode} (${value.countryName})`}
                                                        value={value.countryCode} key={index}/>
                                )}
                            </Picker>
                            <TextInput
                                value={values.phoneNumber}
                                onChangeText={handleChange('phoneNumber')}
                                onBlur={handleBlur('phoneNumber')}
                                style={[styles.textInput, styles.textPhoneNumber]} />
                        </View>
                    </View>
                </View>
            )}
        </Formik>
    );
}

const styles = StyleSheet.create({
    container: {
        padding: 20,
        paddingBottom: 40,
        paddingTop: 0
    },
    labeledInput: {
        marginTop: 20,
    },
    inputLabel: {
        fontWeight: 'bold'
    },
    textInput: {
        borderWidth: 0,
        marginTop: 10,
        borderBottomWidth: 1,
        paddingLeft: 10,
        padding: 5,
    },
    textCountryCode: {
        flex: 1,
        borderRightWidth: 1,
        textAlign: 'right',
    },
    textPhoneNumber: {
        flex: 2,
    },
    phoneNumberView: {
        flexDirection: "row"
    }
});
