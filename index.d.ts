declare module 'react-native-widget-reload' {
  const nativeModule: {
    reloadAllWidgets: () => void;
    androidConfigure: (widgetClassName: string, widgetIdsExtra: string) => void;
  }

  export default nativeModule;
}
