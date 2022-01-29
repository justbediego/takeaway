import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';
import {Text, TextInput, View} from '../components/Themed';
import React from "react";

const LabeledInput = ({label, value}) => {
    return (
        <View style={styles.labeledInput}>
            <Text style={styles.inputLabel}>{label}</Text>
            <TextInput style={styles.textInput}>{value}</TextInput>
        </View>
    );
}

export default function EditProfileScreen() {
    return (
        <View style={styles.container}>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'auto'}/>
            <LabeledInput label={"First Name"} value={"Salam"}/>
            <LabeledInput label={"First Name"} value={"Salam"}/>
            <LabeledInput label={"First Name"} value={"Salam"}/>
            <LabeledInput label={"First Name"} value={"Salam"}/>
            <LabeledInput label={"First Name"} value={"Salam"}/>
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
    }
});
