import {StyleSheet,ScrollView} from 'react-native';

import {View} from '../components/Themed';
import React from "react";
import ItemBox from "../components/ItemBox";

export default function TabOneScreen({navigation}) {
    return (
        <View style={styles.container}>
            <ScrollView style={styles.itemBoxContainer} showsVerticalScrollIndicator={false} contentContainerStyle={{flexWrap:'wrap',  flexDirection: 'row'}}>
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
    },
    itemBoxContainer: {
        padding: 10,
    },
    itemBox: {
        flexBasis: '45%',
        margin: 2,
        backgroundColor: 'orange',
    }
});
