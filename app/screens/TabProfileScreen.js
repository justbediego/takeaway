import {StyleSheet} from 'react-native';

import {Image, Text, View} from '../components/Themed';
import React from "react";
import Colors from "../constants/Colors";
import useColorScheme from "../hooks/useColorScheme";

export default function TabProfileScreen() {
    const colorScheme = useColorScheme();
    return (
        <View style={styles.container}>
            <Image source={{
                uri: "https://www.fairtravel4u.org/wp-content/uploads/2018/06/sample-profile-pic.png",
            }} style={{
                ...styles.profilePicture,
                borderColor: Colors[colorScheme].tint
            }}/>
            <Text style={styles.fullName}>Hossein Alizadeh</Text>
            <Text style={styles.username}>@justbediego</Text>
            <Text style={styles.username}>+4915223339850</Text>
            <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)"/>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'flex-start',
    },
    profilePicture: {
        marginTop: 50,
        height: 100,
        width: 100,
        borderRadius: 50,
        borderWidth: 3
    },
    fullName: {
        fontSize: 20,
        fontWeight: 'bold',
    },
    username:{

    },
    separator: {
        marginVertical: 30,
        height: 1,
        width: '80%',
    },
});
