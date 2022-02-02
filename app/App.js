import {StatusBar} from 'expo-status-bar';
import {SafeAreaProvider} from 'react-native-safe-area-context';

import React from "react";
import useCachedResources from './hooks/useCachedResources';
import Navigation from './navigation';
import i18n from "i18next";
import {initReactI18next} from "react-i18next";
import translations from "./translations";
import {Provider} from 'react-redux';
import store from './store';

i18n
    .use(initReactI18next)
    .init({
        compatibilityJSON: 'v3',
        resources: translations,
        lng: "en",
        fallbackLng: "en",
        interpolation: {
            escapeValue: false // react already safes from xss => https://www.i18next.com/translation-function/interpolation#unescape
        }
    });

export default function App() {
    const isLoadingComplete = useCachedResources();
    // const colorScheme = useColorScheme();
    const colorScheme = 'light';

    if (!isLoadingComplete) {
        return null;
    } else {
        return (
            <SafeAreaProvider>
                <Provider store={store}>
                    <Navigation colorScheme={colorScheme}/>
                    <StatusBar style={'dark'}/>
                </Provider>
            </SafeAreaProvider>
        );
    }
}
