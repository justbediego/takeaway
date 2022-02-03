import {Platform, StyleSheet} from 'react-native';
import React, {useLayoutEffect} from "react";
import {Image, View} from "../components/Themed";
import {getImageSource} from "../services";
import {StatusBar} from "expo-status-bar";


export default function ShowProfilePictureScreen({route, navigation}) {

    const {title, picId, picKey} = route.params;

    useLayoutEffect(() => {
        navigation.setOptions({title});
    }, [navigation, title]);

    return (
        <>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'dark'}/>
            <View style={styles.container}>
                <Image
                    source={{uri: getImageSource(picId, picKey)}}
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
        flexGrow: 1,
        resizeMode: 'contain',
    }
});
