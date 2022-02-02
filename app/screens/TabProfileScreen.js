import {StyleSheet, View as DefaultView} from 'react-native';

import {Image, Pressable, Text, View} from '../components/Themed';
import React, {useEffect, useRef} from "react";
import Colors from "../constants/Colors";

import {useDispatch, useSelector} from 'react-redux';
import {update as updateBasicInfoSlice} from '../store/basicInfoSlice';
import {deleteProfilePicture, getBasicInfo, getImageSource, updateProfilePicture} from "../services";
import {FontAwesome} from "@expo/vector-icons";
import Modal from 'react-native-modalbox';
import {useTranslation} from "react-i18next";
import * as ImagePicker from 'expo-image-picker';

export default function TabProfileScreen({navigation}) {
    const {t} = useTranslation();
    // const colorScheme = useColorScheme();
    const colorScheme = 'light';
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
        try {
            await deleteProfilePicture();
            await refreshBasicInfo();
        } catch (e) {
            // todo
            console.log(e.translation);
        }
    }

    const uploadProfilePicPressed = async () => {
        const result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: [1, 1],
            quality: 1,
        });

        if (result.cancelled || !result?.uri) {
            return;
        }
        const uri = result.uri;
        const name = result.uri.split('/').pop();

        const formData = new FormData();
        formData.append('file', {
            uri,
            name,
            type: 'image/*'
        });

        try {
            await updateProfilePicture(formData);
            await refreshBasicInfo();
            picOptions.current.close();
        } catch (e) {
            // todo
            console.log(e.translation);
            console.log(e.message);
        }
    }

    const openProfilePictureModal = () => {
        if (basicInfo?.profilePictureOriginalId) {
            navigation.navigate('ShowProfilePictureModal', {
                title: t('modalProfilePictureTitle'),
                picId: basicInfo.profilePictureOriginalId,
                picKey: basicInfo?.profilePictureOriginalKey
            });
        }
    }

    useEffect(() => {
        refreshBasicInfo();
    }, []);

    return (
        <View style={styles.container}>
            <Pressable onPress={openProfilePictureModal}>
                <Image
                    source={basicInfo?.profilePictureId ?
                        {uri: getImageSource(basicInfo?.profilePictureId, basicInfo?.profilePictureKey)} :
                        require('../assets/images/anonymous.png')
                    }
                    style={{
                        ...styles.profilePicture,
                        borderColor: Colors[colorScheme].imageBorder
                    }}
                />
            </Pressable>
            <Pressable onPress={() => picOptions.current.open()} style={styles.pictureEditBtn}>
                <FontAwesome
                    name="arrow-circle-o-up"
                    style={{
                        color: Colors[colorScheme].text,
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
                        <FontAwesome
                            name="upload"
                            style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnIcon}}/>
                        <Text style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnText}}>
                            {t('uploadProfilePictureBtn')}
                        </Text>
                    </DefaultView>
                </Pressable>
                <Pressable onPress={deleteProfilePicPressed}>
                    <DefaultView style={styles.pictureOptionBtn}>
                        <FontAwesome
                            name="trash"
                            style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnIcon}}/>
                        <Text style={{color: Colors[colorScheme].tint, ...styles.pictureOptionBtnText}}>
                            {t('deleteProfilePictureBtn')}
                        </Text>
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
        height: 150,
        flexDirection: 'column',
        justifyContent: 'space-around',
        borderTopRightRadius: 20,
        borderTopLeftRadius: 20,
        paddingBottom: 30
    },
    pictureOptionBtn: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
    },
    pictureOptionBtnText: {
        fontSize: 15
    },
    pictureOptionBtnIcon: {
        fontSize: 25,
        marginRight: 10
    }
});
