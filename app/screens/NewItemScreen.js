import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';

import {Text, View} from '../components/Themed';
import React from "react";

export default function NewItemScreen() {
    return (
        <>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'dark'}/>
            <View style={styles.container}>
                <Text style={styles.title}>Modal</Text>
                <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)"/>
            </View>
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
    },
    separator: {
        marginVertical: 30,
        height: 1,
        width: '80%',
    },
});
