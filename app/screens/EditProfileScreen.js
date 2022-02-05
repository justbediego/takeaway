import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';
import {Button, Picker, Text, TextInput, View} from '../components/Themed';
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {getBasicInfo, getCountryCodes, updateBasicInfo} from "../services";
import {useDispatch, useSelector} from "react-redux";
import {update as updateBasicInfoSlice} from "../store/basicInfoSlice";
import {Formik} from "formik";
import Colors from "../constants/Colors";

const LabeledInput = ({label, value, ...otherProps}) => {
    return (
        <View style={styles.labeledInput}>
            <Text style={styles.inputLabel}>{label}</Text>
            <TextInput style={styles.textInput} {...otherProps}>{value}</TextInput>
        </View>
    );
}

export default function EditProfileScreen({navigation}) {
    const {t} = useTranslation();
    // const colorScheme = useColorScheme();
    const colorScheme = 'light';
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

    const refreshCountryCodes = async () => {
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

    const submitBasicInfo = async (values) => {
        try {
            await updateBasicInfo({
                firstName: values.firstName,
                lastName: values.lastName,
                phoneNumber: values.phoneNumber,
                phoneNumberCountryCode: values.phoneNumberCountryCode
            });
            await refreshBasicInfo();
            navigation.goBack(null);
        } catch (e) {
            // todo
            console.log(e.translation);
        }
    }

    useEffect(() => {
        refreshBasicInfo();
        refreshCountryCodes();
    }, [])

    return (
        <>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'dark'}/>
            <Formik initialValues={basicInfo} onSubmit={submitBasicInfo}>
                {({handleChange, setFieldValue, handleBlur, handleSubmit, values}) => (
                    <View style={styles.container}>
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
                        {countryCodes?.map &&
                        <View style={styles.labeledInput}>
                            <Text style={styles.inputLabel}>{t('labelPhoneNumber')}</Text>
                            <View style={styles.phoneNumberView}>
                                <Picker
                                    value={values.phoneNumberCountryCode}
                                    onValueChange={(value) => setFieldValue('phoneNumberCountryCode', value)}
                                    placeholder={{}}
                                    style={styles.pickerCountryCode}
                                    textInputStyle={styles.textInput}
                                    items={countryCodes.map((value, index) =>
                                        ({
                                            label: `${value.countryName} (${value.countryCode})`,
                                            value: value.countryCode,
                                            inputLabel: value.countryCode,
                                            key: index
                                        })
                                    )}>
                                </Picker>
                                <TextInput
                                    value={values.phoneNumber}
                                    onChangeText={handleChange('phoneNumber')}
                                    onBlur={handleBlur('phoneNumber')}
                                    style={[styles.textInput, styles.textPhoneNumber]}/>
                            </View>
                        </View>}
                        <Button
                            title={t('submitButton')}
                            style={{
                                color: Colors[colorScheme].tint,
                                ...styles.submitButton
                            }}
                            onPress={handleSubmit}
                        />
                    </View>
                )}
            </Formik>
        </>
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
    submitButton: {
        marginTop: 40,
        textTransform: 'uppercase',
        textAlign: 'center'
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
        height: 40
    },
    pickerCountryCode: {
        flex: 1,
        marginRight:10
    },
    textPhoneNumber: {
        flex: 5,
    },
    phoneNumberView: {
        flexDirection: "row",
    }
});
