import {Platform, StyleSheet} from 'react-native';
import React, {useEffect, useLayoutEffect, useState} from "react";
import {Image, View} from "../components/Themed";
import {getAttachmentLink} from "../services";
import {StatusBar} from "expo-status-bar";


export default function ShowProfilePictureScreen({route, navigation}) {

    const {title, picId, picKey} = route.params;
    const [picLink, setPicLink] = useState(null);

    const getPictureLink = async () => {
        try {
            const imageLink = await getAttachmentLink(picId, picKey);
            setPicLink(imageLink);
        } catch (e) {
            // todo
            console.log(e.translation);
        }
    }

    useEffect(() => {
        getPictureLink();
    }, []);


    useLayoutEffect(() => {
        navigation.setOptions({title});
    }, [navigation, title]);

    return (
        <>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'dark'}/>
            <View style={styles.container}>
                <Image
                    source={picLink ? {uri: picLink} : require('../assets/images/anonymous.png')}
                    style={styles.profilePicture}
                />
            </View>
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: '#000000',
        flexGrow: 1,
        justifyContent: 'center',
    },
    profilePicture: {
        width: '100%',
        height: '100%',
        resizeMode: 'contain',
    }
});
