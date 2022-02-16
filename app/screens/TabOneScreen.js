import {StyleSheet,ScrollView} from 'react-native';

import {View} from '../components/Themed';
import React from "react";
import ItemBox from "../components/ItemBox";

export default function TabOneScreen({navigation}) {
    return (
        <View style={styles.container}>
            <ScrollView style={styles.itemBoxContainer} showsVerticalScrollIndicator={false}>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
                <ItemBox style={styles.itemBox}></ItemBox>
            </ScrollView>

        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // alignItems: 'center',
        // justifyContent: 'flex-start',
    },
    itemBoxContainer: {
        // flexDirection: 'row',
        // flexWrap: 'wrap',
        // padding: 10,
    },
    itemBox: {
        flexBasis: '40%',
        margin: 10,
        backgroundColor: 'orange',
        textAlign: 'center',
    }

});
