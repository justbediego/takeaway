import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';
import {Picker, Text, TextInput, View} from '../components/Themed';
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";

import {Picker as DefaultPicker} from '@react-native-picker/picker'
import {getCountryCodes} from "../services";


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
        updateCountryCodes();
    }, [])

    return (
        <View style={styles.container}>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'auto'}/>
            <LabeledInput label={t('labelFirstName')} value={"Salam"}/>
            <LabeledInput label={t('labelLastName')} value={"Salam"}/>
            <View style={styles.labeledInput}>
                <Text style={styles.inputLabel}>{t('labelPhoneNumber')}</Text>
                <View style={styles.phoneNumberView}>
                    <Picker mode="dialog" style={[styles.textInput, styles.textCountryCode]}>
                        {countryCodes && countryCodes.map((value, index) =>
                            <DefaultPicker.Item label={`${value.countryCode} (${value.countryName})`}
                                                value={value.countryCode} key={index}/>
                        )}
                    </Picker>
                    <TextInput style={[styles.textInput, styles.textPhoneNumber]}>Salam</TextInput>
                </View>
            </View>
        </View>
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
        textAlign: 'right'
    },
    textPhoneNumber: {
        flex: 2,
    },
    phoneNumberView: {
        flexDirection: "row"
    }
});
