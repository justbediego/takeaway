import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';
import {Picker, Text, TextInput, View} from '../components/Themed';
import React from "react";
import {useTranslation} from "react-i18next";

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

    return (
        <View style={styles.container}>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'auto'}/>
            <LabeledInput label={t('labelFirstName')} value={"Salam"}/>
            <LabeledInput label={t('labelLastName')} value={"Salam"}/>
            <View style={styles.labeledInput}>
                <Text style={styles.inputLabel}>{t('labelPhoneNumber')}</Text>
                <View style={styles.phoneNumberView}>
                    <Picker type={'asc'} style={[styles.textInput, styles.textCountryCode]}>
                        <Picker.Item label="Java" value="java" />
                        <Picker.Item label="JavaScript" value="js" />
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
        borderRightWidth: 1
    },
    textPhoneNumber: {
        flex: 4,
    },
    phoneNumberView: {
        flexDirection: "row"
    }
});
