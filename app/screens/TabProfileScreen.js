import {StyleSheet} from 'react-native';

import {Image, Text, View, Button} from '../components/Themed';
import React, {useEffect} from "react";
import Colors from "../constants/Colors";
import useColorScheme from "../hooks/useColorScheme";

import {useDispatch, useSelector} from 'react-redux';
import {update as updateBasicInfoSlice} from '../store/basicInfoSlice';
import {getBasicInfo} from "../services";

export default function TabProfileScreen() {
    const colorScheme = useColorScheme();
    const basicInfo = useSelector((state) => state.basicInfo.value)
    const dispatch = useDispatch()

    const refreshBasicInfo = async () => {
        try{
            const response = await getBasicInfo();
            await dispatch(updateBasicInfoSlice(response));
        }catch (e) {
            console.log(e.translation);
        }
    }

    useEffect(() => {
        refreshBasicInfo();
    }, []);

    return (
        <View style={styles.container}>
            <Image source={{
                uri: "https://www.fairtravel4u.org/wp-content/uploads/2018/06/sample-profile-pic.png",
            }} style={{
                ...styles.profilePicture,
                borderColor: Colors[colorScheme].tint
            }}/>
            <Text style={styles.fullName}>{basicInfo.firstName} {basicInfo.lastName}</Text>
            <Text style={styles.username}>@{basicInfo.username}</Text>
            <Text style={styles.username}>{basicInfo.phoneNumberCountryCode}{basicInfo.phoneNumber}</Text>
            <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)"/>
            <Button title="Reload" onPress={refreshBasicInfo} />
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
    username: {},
    separator: {
        marginVertical: 30,
        height: 1,
        width: '80%',
    },
});
