import {StyleSheet} from 'react-native';

import {Image, Pressable, Text, View} from '../components/Themed';
import React, {useEffect, useRef} from "react";
import Colors from "../constants/Colors";
import useColorScheme from "../hooks/useColorScheme";

import {useDispatch, useSelector} from 'react-redux';
import {update as updateBasicInfoSlice} from '../store/basicInfoSlice';
import {getBasicInfo} from "../services";
import {FontAwesome, MaterialCommunityIcons} from "@expo/vector-icons";
import Modal from 'react-native-modalbox';
import {useTranslation} from "react-i18next";
import {View as DefaultView} from 'react-native';

export default function TabProfileScreen() {
    const {t} = useTranslation();
    const colorScheme = useColorScheme();
    const basicInfo = useSelector((state) => state.basicInfo.value)
    const dispatch = useDispatch();
    const picOptions = useRef();


    const refreshBasicInfo = async () => {
        try {
            const response = await getBasicInfo();
            await dispatch(updateBasicInfoSlice(response));
        } catch (e) {
            // todo
            console.log(e.translation);
        }
    }

    const deleteProfilePicPressed = async () => {

    }

    const uploadProfilePicPressed = async () => {

    }

    useEffect(() => {
        refreshBasicInfo();
    }, []);

    return (
        <View style={styles.container}>
            <Image
                source={basicInfo?.profilePictureId ?
                    {uri: "https://www.fairtravel4u.org/wp-content/uploads/2018/06/sample-profile-pic.png"} :
                    require('../assets/images/anonymous.png')
                }
                style={{
                    ...styles.profilePicture,
                    borderColor: Colors[colorScheme].imageBorder
                }}
            />
            <Pressable onPress={() => picOptions.current.open()} style={styles.pictureEditBtn}>
                <MaterialCommunityIcons
                    name="pencil-circle"
                    style={{
                        color: Colors[colorScheme].tint,
                        ...styles.pictureEditIcon
                    }}/>
            </Pressable>
            <Text style={styles.fullName}>{basicInfo.firstName} {basicInfo.lastName}</Text>
            <Text style={styles.username}>@{basicInfo.username}</Text>
            <Text style={styles.username}>{basicInfo.phoneNumberCountryCode}{basicInfo.phoneNumber}</Text>
            <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)"/>
            <Modal style={{...styles.picOptions, backgroundColor: Colors[colorScheme].modalBackground}}
                   position={"bottom"} ref={picOptions}>
                <Pressable onPress={uploadProfilePicPressed}>
                    <DefaultView style={styles.pictureOptionBtn}>
                        <FontAwesome name="upload"
                                     style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnIcon}}/>
                        <Text
                            style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnText}}>{t('uploadProfilePictureBtn')}</Text>
                    </DefaultView>
                </Pressable>
                <Pressable onPress={deleteProfilePicPressed}>
                    <DefaultView style={styles.pictureOptionBtn}>
                        <FontAwesome name="trash"
                                     style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnIcon}}/>
                        <Text
                            style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnText}}>{t('deleteProfilePictureBtn')}</Text>
                    </DefaultView>
                </Pressable>
            </Modal>
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
        borderWidth: 2,
    },
    fullName: {
        marginTop: 10,
        fontSize: 20,
        fontWeight: 'bold',
    },
    username: {},
    separator: {
        marginVertical: 30,
        height: 1,
        width: '80%',
    },
    pictureEditBtn: {
        marginTop: -30,
        marginLeft: 70,
    },
    pictureEditIcon: {
        fontSize: 30
    },
    picOptions: {
        height: 100,
        flexDirection: 'column',
        justifyContent:'space-around'
    },
    pictureOptionBtn: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
    },
    pictureOptionBtnText: {
        fontSize: 15,
        textTransform: 'uppercase'
    },
    pictureOptionBtnIcon: {
        fontSize: 25,
        marginRight: 10
    }
});
