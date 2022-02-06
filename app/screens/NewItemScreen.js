import {StatusBar} from 'expo-status-bar';
import {Platform, StyleSheet} from 'react-native';

import {Image, Pressable, Text, TextInput, View} from '../components/Themed';
import React from "react";
import {useTranslation} from "react-i18next";
import {Formik} from "formik";
import Colors from "../constants/Colors";
import {Ionicons} from "@expo/vector-icons";

const LabeledInput = ({label, value, ...otherProps}) => {
    return (
        <View style={styles.labeledInput}>
            <Text style={styles.inputLabel}>{label}</Text>
            <TextInput style={styles.textInput} {...otherProps}>{value}</TextInput>
        </View>
    );
}

export default function NewItemScreen() {
    const {t} = useTranslation();
    const colorScheme = 'light';

    const submitNewItem = async (values) => {

    }


    return (
        <>
            <StatusBar style={Platform.OS === 'ios' ? 'light' : 'dark'}/>
            <Formik initialValues={{}} onSubmit={submitNewItem}>
                {({handleChange, setFieldValue, handleBlur, handleSubmit, values}) => (
                    <View style={styles.container}>
                        <Image
                            source={
                                require('../assets/images/anonymous.png')
                            }
                            style={{
                                ...styles.newItemPicture,
                                borderColor: Colors[colorScheme].imageBorder
                            }}
                        />
                        <Pressable style={styles.newItemAddBtn}>
                            <Ionicons
                                name="add-circle-sharp"
                                size={25}
                                style={{
                                    color: Colors[colorScheme].text,
                                    borderColor: Colors[colorScheme].imageBorder,
                                    ...styles.pictureEditIcon
                                }}/>
                        </Pressable>

                        <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)"/>
                        <LabeledInput label={t('labelTitle')}/>
                        <LabeledInput label={t('labelDescription')}/>
                        {/*<Text>{t('labelCategory')}</Text>*/}
                        {/*<Text style={styles.inputLabel}>{t('labelCategory')}</Text>*/}
                        {/*<View style={styles.categoryView}>*/}
                        {/*    <Picker>*/}
                        {/*        items={countryCodes.map((value, index) =>*/}
                        {/*        ({*/}
                        {/*            label: `${value.countryName} (${value.countryCode})`,*/}
                        {/*            value: value.countryCode,*/}
                        {/*            inputLabel: value.countryCode,*/}
                        {/*            key: index*/}
                        {/*        })*/}
                        {/*    )}>*/}
                        {/*    </Picker>*/}
                        {/*</View>*/}
                        <LabeledInput label={t('labelLocation')}/>
                    </View>
                )}
            </Formik>
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        padding: 20,
        paddingBottom: 40,
        paddingTop: 0
    },
    newItemPicture: {
        width: 80,
        height: 80,
        resizeMode: 'contain',
        borderRadius: 5,
        overflow: 'hidden',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
    },
    newItemAddBtn: {
        marginTop: -15,
        marginLeft: 65,
    },
    separator: {
        marginVertical: 20,
        height: 1,
    },
    labeledInput: {
        marginTop: 20,
    },
    textInput: {
        borderWidth: 0,
        marginTop: 10,
        borderBottomWidth: 1,
        paddingLeft: 10,
        padding: 5,
        height: 40
    }
    // categoryView: {
    //     flexDirection: "row",
    // }
});
