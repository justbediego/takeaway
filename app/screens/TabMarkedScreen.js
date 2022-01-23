import {StyleSheet} from 'react-native';

import {Text, View} from '../components/Themed';
import React from "react";

export default function TabMarkedScreen() {
    return (
        <View style={styles.container}>
            <Text>Marked</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
});
