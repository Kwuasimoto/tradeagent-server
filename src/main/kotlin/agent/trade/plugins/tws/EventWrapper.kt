package agent.trade.plugins.tws;

import com.ib.client.*

class EventWrapper : EWrapper {

    private var NextOrderId = -1

    override fun nextValidId(orderId: Int) {
        NextOrderId = orderId
        println(EWrapperMsgGenerator.nextValidId(orderId))
    }

    override fun error(e: Exception?) {
        println(EWrapperMsgGenerator.error(e))
    }

    override fun error(id: Int, errorCode: Int, errorMsg: String?, advancedOrderRejectJson: String?) {
        println(EWrapperMsgGenerator.error(id, errorCode, errorMsg, advancedOrderRejectJson))
    }

    override fun connectionClosed() {
        println(EWrapperMsgGenerator.connectionClosed())
    }

    override fun error(str: String?) {
        println(EWrapperMsgGenerator.error(str))
    }

    override fun tickPrice(tickerId: Int, field: Int, price: Double, attribs: TickAttrib?) {
        println(EWrapperMsgGenerator.tickPrice(tickerId, field, price, attribs))
    }

    override fun tickSize(tickerId: Int, field: Int, size: Decimal?) {
        println(EWrapperMsgGenerator.tickSize(tickerId, field, size))
    }

    override fun tickOptionComputation(
        tickerId: Int,
        field: Int,
        tickAttrib: Int,
        impliedVol: Double,
        delta: Double,
        optPrice: Double,
        pvDividend: Double,
        gamma: Double,
        vega: Double,
        theta: Double,
        undPrice: Double
    ) {
        println(
            EWrapperMsgGenerator.tickOptionComputation(
                tickerId,
                field,
                tickAttrib,
                impliedVol,
                delta,
                optPrice,
                pvDividend,
                gamma,
                vega,
                theta,
                undPrice
            )
        )
    }

    override fun tickGeneric(tickerId: Int, tickType: Int, value: Double) {
        println(EWrapperMsgGenerator.tickGeneric(tickerId, tickType, value))
    }

    override fun tickString(tickerId: Int, tickType: Int, value: String?) {
        println(EWrapperMsgGenerator.tickString(tickerId, tickType, value))
    }

    override fun tickEFP(
        tickerId: Int,
        tickType: Int,
        basisPoints: Double,
        formattedBasisPoints: String?,
        impliedFuture: Double,
        holdDays: Int,
        futureLastTradeDate: String?,
        dividendImpact: Double,
        dividendsToLastTradeDate: Double
    ) {
        println(
            EWrapperMsgGenerator.tickEFP(
                tickerId,
                tickType,
                basisPoints,
                formattedBasisPoints,
                impliedFuture,
                holdDays,
                futureLastTradeDate,
                dividendImpact,
                dividendsToLastTradeDate
            )
        )
    }

    override fun orderStatus(
        orderId: Int,
        status: String?,
        filled: Decimal?,
        remaining: Decimal?,
        avgFillPrice: Double,
        permId: Int,
        parentId: Int,
        lastFillPrice: Double,
        clientId: Int,
        whyHeld: String?,
        mktCapPrice: Double
    ) {
        println(
            EWrapperMsgGenerator.orderStatus(
                orderId,
                status,
                filled,
                remaining,
                avgFillPrice,
                permId,
                parentId,
                lastFillPrice,
                clientId,
                whyHeld,
                mktCapPrice
            )
        )
    }

    override fun openOrder(orderId: Int, contract: Contract?, order: Order?, orderState: OrderState?) {
        println(EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState))
    }

    override fun openOrderEnd() {
        println(EWrapperMsgGenerator.openOrderEnd())
    }

    override fun updateAccountValue(key: String?, value: String?, currency: String?, accountName: String?) {
        println(EWrapperMsgGenerator.updateAccountValue(key, value, currency, accountName))
    }

    override fun updatePortfolio(
        contract: Contract?,
        position: Decimal?,
        marketPrice: Double,
        marketValue: Double,
        averageCost: Double,
        unrealizedPNL: Double,
        realizedPNL: Double,
        accountName: String?
    ) {
        println(
            EWrapperMsgGenerator.updatePortfolio(
                contract,
                position,
                marketPrice,
                marketValue,
                averageCost,
                unrealizedPNL,
                realizedPNL,
                accountName
            )
        )
    }

    override fun updateAccountTime(timeStamp: String?) {
        println(EWrapperMsgGenerator.updateAccountTime(timeStamp))
    }

    override fun accountDownloadEnd(accountName: String?) {
        println(EWrapperMsgGenerator.accountDownloadEnd(accountName))
    }

    override fun contractDetails(reqId: Int, contractDetails: ContractDetails?) {
        println(EWrapperMsgGenerator.contractDetails(reqId, contractDetails))
    }

    override fun bondContractDetails(reqId: Int, contractDetails: ContractDetails?) {
        println(EWrapperMsgGenerator.bondContractDetails(reqId, contractDetails))
    }

    override fun contractDetailsEnd(reqId: Int) {
        println(EWrapperMsgGenerator.contractDetailsEnd(reqId))
    }

    override fun execDetails(reqId: Int, contract: Contract?, execution: Execution?) {
        println(EWrapperMsgGenerator.execDetails(reqId, contract, execution))
    }

    override fun execDetailsEnd(reqId: Int) {
        println(EWrapperMsgGenerator.execDetailsEnd(reqId))
    }

    override fun updateMktDepth(
        tickerId: Int,
        position: Int,
        operation: Int,
        side: Int,
        price: Double,
        size: Decimal?
    ) {
        println(EWrapperMsgGenerator.updateMktDepth(tickerId, position, operation, side, price, size))
    }

    override fun updateMktDepthL2(
        tickerId: Int,
        position: Int,
        marketMaker: String?,
        operation: Int,
        side: Int,
        price: Double,
        size: Decimal?,
        isSmartDepth: Boolean
    ) {
        println(
            EWrapperMsgGenerator.updateMktDepthL2(
                tickerId,
                position,
                marketMaker,
                operation,
                side,
                price,
                size,
                isSmartDepth
            )
        )
    }

    override fun updateNewsBulletin(msgId: Int, msgType: Int, message: String?, origExchange: String?) {
        println(EWrapperMsgGenerator.updateNewsBulletin(msgId, msgType, message, origExchange))
    }

    override fun managedAccounts(accountsList: String?) {
        println(EWrapperMsgGenerator.managedAccounts(accountsList))
    }

    override fun receiveFA(faDataType: Int, xml: String?) {
        println(EWrapperMsgGenerator.receiveFA(faDataType, xml))
    }

    override fun historicalData(reqId: Int, bar: Bar) {
        println(
            EWrapperMsgGenerator.historicalData(
                reqId,
                bar.time(),
                bar.open(),
                bar.high(),
                bar.low(),
                bar.close(),
                bar.volume(),
                bar.count(),
                bar.wap()
            )
        )
    }

    override fun scannerParameters(xml: String?) {
        println(EWrapperMsgGenerator.scannerParameters(xml))
    }

    override fun scannerData(
        reqId: Int,
        rank: Int,
        contractDetails: ContractDetails?,
        distance: String?,
        benchmark: String?,
        projection: String?,
        legsStr: String?
    ) {
        println(
            EWrapperMsgGenerator.scannerData(
                reqId,
                rank,
                contractDetails,
                distance,
                benchmark,
                projection,
                legsStr
            )
        )
    }

    override fun scannerDataEnd(reqId: Int) {
        println(EWrapperMsgGenerator.scannerDataEnd(reqId))
    }

    override fun realtimeBar(
        reqId: Int,
        time: Long,
        open: Double,
        high: Double,
        low: Double,
        close: Double,
        volume: Decimal?,
        wap: Decimal?,
        count: Int
    ) {
        println(EWrapperMsgGenerator.realtimeBar(reqId, time, open, high, low, close, volume, wap, count))
    }

    override fun currentTime(time: Long) {
        println(EWrapperMsgGenerator.currentTime(time))
    }

    override fun fundamentalData(reqId: Int, data: String?) {
        println(EWrapperMsgGenerator.fundamentalData(reqId, data))
    }

    override fun deltaNeutralValidation(reqId: Int, deltaNeutralContract: DeltaNeutralContract?) {
        println(EWrapperMsgGenerator.deltaNeutralValidation(reqId, deltaNeutralContract))
    }

    override fun tickSnapshotEnd(reqId: Int) {
        println(EWrapperMsgGenerator.tickSnapshotEnd(reqId))
    }

    override fun marketDataType(reqId: Int, marketDataType: Int) {
        println(EWrapperMsgGenerator.marketDataType(reqId, marketDataType))
    }

    override fun commissionReport(commissionReport: CommissionReport?) {
        println(EWrapperMsgGenerator.commissionReport(commissionReport))
    }

    override fun position(account: String?, contract: Contract?, pos: Decimal?, avgCost: Double) {
        println(EWrapperMsgGenerator.position(account, contract, pos, avgCost))
    }

    override fun positionEnd() {
        println(EWrapperMsgGenerator.positionEnd())
    }

    override fun accountSummary(reqId: Int, account: String?, tag: String?, value: String?, currency: String?) {
        println(EWrapperMsgGenerator.accountSummary(reqId, account, tag, value, currency))
    }

    override fun accountSummaryEnd(reqId: Int) {
        println(EWrapperMsgGenerator.accountSummaryEnd(reqId))
    }

    override fun verifyMessageAPI(apiData: String?) {}

    override fun verifyCompleted(isSuccessful: Boolean, errorText: String?) {}

    override fun verifyAndAuthMessageAPI(apiData: String?, xyzChallenge: String?) {}

    override fun verifyAndAuthCompleted(isSuccessful: Boolean, errorText: String?) {}

    override fun displayGroupList(reqId: Int, groups: String?) {}

    override fun displayGroupUpdated(reqId: Int, contractInfo: String?) {}

    override fun positionMulti(
        reqId: Int,
        account: String?,
        modelCode: String?,
        contract: Contract?,
        pos: Decimal?,
        avgCost: Double
    ) {
        println(EWrapperMsgGenerator.positionMulti(reqId, account, modelCode, contract, pos, avgCost))
    }

    override fun positionMultiEnd(reqId: Int) {
        println(EWrapperMsgGenerator.positionMultiEnd(reqId))
    }

    override fun accountUpdateMulti(
        reqId: Int,
        account: String?,
        modelCode: String?,
        key: String?,
        value: String?,
        currency: String?
    ) {
        println(EWrapperMsgGenerator.accountUpdateMulti(reqId, account, modelCode, key, value, currency))
    }

    override fun accountUpdateMultiEnd(reqId: Int) {
        println(EWrapperMsgGenerator.accountUpdateMultiEnd(reqId))
    }

    override fun connectAck() {}

    override fun securityDefinitionOptionalParameter(
        reqId: Int, exchange: String?, underlyingConId: Int, tradingClass: String?,
        multiplier: String?, expirations: Set<String?>?, strikes: Set<Double?>?
    ) {
        println(
            EWrapperMsgGenerator.securityDefinitionOptionalParameter(
                reqId,
                exchange,
                underlyingConId,
                tradingClass,
                multiplier,
                expirations,
                strikes
            )
        )
    }

    override fun securityDefinitionOptionalParameterEnd(reqId: Int) {
        println(EWrapperMsgGenerator.securityDefinitionOptionalParameterEnd(reqId))
    }

    override fun softDollarTiers(reqId: Int, tiers: Array<SoftDollarTier?>?) {
        println(EWrapperMsgGenerator.softDollarTiers(reqId, tiers))
    }

    override fun familyCodes(familyCodes: Array<FamilyCode?>?) {
        println(EWrapperMsgGenerator.familyCodes(familyCodes))
    }

    override fun symbolSamples(reqId: Int, contractDescriptions: Array<ContractDescription?>?) {
        println(EWrapperMsgGenerator.symbolSamples(reqId, contractDescriptions))
    }

    override fun historicalDataEnd(reqId: Int, startDateStr: String?, endDateStr: String?) {
        println(EWrapperMsgGenerator.historicalDataEnd(reqId, startDateStr, endDateStr))
    }

    override fun mktDepthExchanges(depthMktDataDescriptions: Array<DepthMktDataDescription?>?) {
        println(EWrapperMsgGenerator.mktDepthExchanges(depthMktDataDescriptions))
    }

    override fun tickNews(
        tickerId: Int, timeStamp: Long, providerCode: String?, articleId: String?, headline: String?,
        extraData: String?
    ) {
        println(EWrapperMsgGenerator.tickNews(tickerId, timeStamp, providerCode, articleId, headline, extraData))
    }

    override fun smartComponents(reqId: Int, theMap: Map<Int?, Map.Entry<String?, Char?>?>?) {
        println(EWrapperMsgGenerator.smartComponents(reqId, theMap))
    }

    override fun tickReqParams(tickerId: Int, minTick: Double, bboExchange: String?, snapshotPermissions: Int) {
        println(EWrapperMsgGenerator.tickReqParams(tickerId, minTick, bboExchange, snapshotPermissions))
    }

    override fun newsProviders(newsProviders: Array<NewsProvider?>?) {
        println(EWrapperMsgGenerator.newsProviders(newsProviders))
    }

    override fun newsArticle(requestId: Int, articleType: Int, articleText: String?) {
        println(EWrapperMsgGenerator.newsArticle(requestId, articleType, articleText))
    }

    override fun historicalNews(
        requestId: Int,
        time: String?,
        providerCode: String?,
        articleId: String?,
        headline: String?
    ) {
        println(EWrapperMsgGenerator.historicalNews(requestId, time, providerCode, articleId, headline))
    }

    override fun historicalNewsEnd(requestId: Int, hasMore: Boolean) {
        println(EWrapperMsgGenerator.historicalNewsEnd(requestId, hasMore))
    }

    override fun headTimestamp(reqId: Int, headTimestamp: String?) {
        println(EWrapperMsgGenerator.headTimestamp(reqId, headTimestamp))
    }

    override fun histogramData(reqId: Int, items: List<HistogramEntry?>?) {
        println(EWrapperMsgGenerator.histogramData(reqId, items))
    }

    override fun historicalDataUpdate(reqId: Int, bar: Bar) {
        historicalData(reqId, bar)
    }

    override fun rerouteMktDataReq(reqId: Int, conId: Int, exchange: String?) {
        println(EWrapperMsgGenerator.rerouteMktDataReq(reqId, conId, exchange))
    }

    override fun rerouteMktDepthReq(reqId: Int, conId: Int, exchange: String?) {
        println(EWrapperMsgGenerator.rerouteMktDepthReq(reqId, conId, exchange))
    }

    override fun marketRule(marketRuleId: Int, priceIncrements: Array<PriceIncrement?>?) {
        println(EWrapperMsgGenerator.marketRule(marketRuleId, priceIncrements))
    }

    override fun pnl(reqId: Int, dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double) {
        println(EWrapperMsgGenerator.pnl(reqId, dailyPnL, unrealizedPnL, realizedPnL))
    }

    override fun pnlSingle(
        reqId: Int,
        pos: Decimal?,
        dailyPnL: Double,
        unrealizedPnL: Double,
        realizedPnL: Double,
        value: Double
    ) {
        println(EWrapperMsgGenerator.pnlSingle(reqId, pos, dailyPnL, unrealizedPnL, realizedPnL, value))
    }

    override fun historicalTicks(reqId: Int, ticks: List<HistoricalTick>, done: Boolean) {
        for (tick in ticks) {
            println(EWrapperMsgGenerator.historicalTick(reqId, tick.time(), tick.price(), tick.size()))
        }
    }

    override fun historicalTicksBidAsk(reqId: Int, ticks: List<HistoricalTickBidAsk>, done: Boolean) {
        for (tick in ticks) {
            println(
                EWrapperMsgGenerator.historicalTickBidAsk(
                    reqId, tick.time(), tick.tickAttribBidAsk(), tick.priceBid(), tick.priceAsk(), tick.sizeBid(),
                    tick.sizeAsk()
                )
            )
        }
    }

    override fun historicalTicksLast(reqId: Int, ticks: List<HistoricalTickLast>, done: Boolean) {
        for (tick in ticks) {
            println(
                EWrapperMsgGenerator.historicalTickLast(
                    reqId, tick.time(), tick.tickAttribLast(), tick.price(), tick.size(), tick.exchange(),
                    tick.specialConditions()
                )
            )
        }
    }

    override fun tickByTickAllLast(
        reqId: Int, tickType: Int, time: Long, price: Double, size: Decimal?, tickAttribLast: TickAttribLast?,
        exchange: String?, specialConditions: String?
    ) {
        println(
            EWrapperMsgGenerator.tickByTickAllLast(
                reqId,
                tickType,
                time,
                price,
                size,
                tickAttribLast,
                exchange,
                specialConditions
            )
        )
    }

    override fun tickByTickBidAsk(
        reqId: Int, time: Long, bidPrice: Double, askPrice: Double, bidSize: Decimal?, askSize: Decimal?,
        tickAttribBidAsk: TickAttribBidAsk?
    ) {
        println(
            EWrapperMsgGenerator.tickByTickBidAsk(
                reqId,
                time,
                bidPrice,
                askPrice,
                bidSize,
                askSize,
                tickAttribBidAsk
            )
        )
    }

    override fun tickByTickMidPoint(reqId: Int, time: Long, midPoint: Double) {
        println(EWrapperMsgGenerator.tickByTickMidPoint(reqId, time, midPoint))
    }

    override fun orderBound(orderId: Long, apiClientId: Int, apiOrderId: Int) {
        println(EWrapperMsgGenerator.orderBound(orderId, apiClientId, apiOrderId))
    }

    override fun completedOrder(contract: Contract?, order: Order?, orderState: OrderState?) {
        println(EWrapperMsgGenerator.completedOrder(contract, order, orderState))
    }

    override fun completedOrdersEnd() {
        println(EWrapperMsgGenerator.completedOrdersEnd())
    }

    override fun replaceFAEnd(reqId: Int, text: String?) {
        println(EWrapperMsgGenerator.replaceFAEnd(reqId, text))
    }

    override fun wshMetaData(reqId: Int, dataJson: String?) {
        println(EWrapperMsgGenerator.wshMetaData(reqId, dataJson))
    }

    override fun wshEventData(reqId: Int, dataJson: String?) {
        println(EWrapperMsgGenerator.wshEventData(reqId, dataJson))
    }

    override fun historicalSchedule(
        reqId: Int,
        startDateTime: String?,
        endDateTime: String?,
        timeZone: String?,
        sessions: List<HistoricalSession?>?
    ) {
        println(EWrapperMsgGenerator.historicalSchedule(reqId, startDateTime, endDateTime, timeZone, sessions))
    }

    override fun userInfo(reqId: Int, whiteBrandingId: String?) {
        println(EWrapperMsgGenerator.userInfo(reqId, whiteBrandingId))
    }
}