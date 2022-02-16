import {StyleSheet,ScrollView} from 'react-native';

import {View} from '../components/Themed';
import React from "react";
import ItemBox from "../components/ItemBox";

export default function TabOneScreen({navigation}) {
    return (
        <View style={styles.container}>
            <ScrollView showsVerticalScrollIndicator={false}
                        contentContainerStyle={styles.itemBoxContainer}>
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
        flexWrap:'wrap',
        flexDirection: 'row',
        justifyContent:'center',
        margin: 10
    },
    itemBox: {
        flexBasis: '45%',
        margin: 3,
        backgroundColor: 'orange',
    }
});
