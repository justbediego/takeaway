import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';
import {Text, TextInput, View} from '../components/Themed';
import React from "react";

export default function EditProfileScreen() {
    return (
        <View style={styles.container}>
            <TextInput label={'ss'}>Salam</TextInput>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'auto'}/>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        padding: 10,
    },
});
